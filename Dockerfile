# Build stage
FROM maven:3.8-openjdk-8 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM arm64v8/openjdk:8-jdk-slim

# Install WildFly
ENV WILDFLY_VERSION=10.0.0.Final
ENV WILDFLY_SHA1=c0dd7552c5207b0d116a9c25eb94d10b4f375549
ENV JBOSS_HOME=/opt/jboss/wildfly

# Add JBoss user
RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin jboss

# Download and install WildFly
RUN apt-get update && apt-get install -y curl && \
    cd $HOME && \
    curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
    sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 && \
    tar xf wildfly-$WILDFLY_VERSION.tar.gz && \
    mkdir -p /opt/jboss && \
    mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME && \
    rm wildfly-$WILDFLY_VERSION.tar.gz && \
    chown -R jboss:0 ${JBOSS_HOME} && \
    chmod -R g+rw ${JBOSS_HOME} && \
    apt-get remove -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Add admin user
USER jboss
RUN $JBOSS_HOME/bin/add-user.sh admin admin123 --silent

# Configure WildFly
RUN sed -i 's/<inet-address value="${jboss.bind.address:127.0.0.1}"/<inet-address value="${jboss.bind.address:0.0.0.0}"/g' $JBOSS_HOME/standalone/configuration/standalone.xml && \
    sed -i 's/<inet-address value="${jboss.bind.address.management:127.0.0.1}"/<inet-address value="${jboss.bind.address.management:0.0.0.0}"/g' $JBOSS_HOME/standalone/configuration/standalone.xml

# Create PostgreSQL module
USER root
RUN mkdir -p $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/
COPY postgresql-42.2.5.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/
RUN echo '<?xml version="1.0" encoding="UTF-8"?>\n\
<module xmlns="urn:jboss:module:1.1" name="org.postgresql">\n\
    <resources>\n\
        <resource-root path="postgresql-42.2.5.jar"/>\n\
    </resources>\n\
    <dependencies>\n\
        <module name="javax.api"/>\n\
        <module name="javax.transaction.api"/>\n\
    </dependencies>\n\
</module>' > $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/module.xml

# Add PostgreSQL driver configuration to standalone.xml
RUN sed -i '/<drivers>/ a\
                    <driver name="postgresql" module="org.postgresql">\n\
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>\n\
                    </driver>' $JBOSS_HOME/standalone/configuration/standalone.xml

# Create deployments directory with correct permissions
RUN mkdir -p $JBOSS_HOME/standalone/deployments && \
    chown -R jboss:jboss $JBOSS_HOME/standalone/deployments && \
    chmod -R 755 $JBOSS_HOME/standalone/deployments

# Copy datasource configuration
COPY --chown=jboss:jboss src/main/resources/META-INF/prover-ds.xml $JBOSS_HOME/standalone/deployments/

# Copy WAR file from builder stage
COPY --from=builder --chown=jboss:jboss /app/target/prover-jsf.war $JBOSS_HOME/standalone/deployments/

# Set environment variables
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0 -Djava.security.egd=file:/dev/./urandom"

# Switch back to jboss user
USER jboss

# Expose ports
EXPOSE 8080 9990

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"] 