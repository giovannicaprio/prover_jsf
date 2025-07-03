FROM jboss/wildfly:10.0.0.Final

# Add admin user
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin123 --silent

# Configure WildFly
RUN sed -i 's/<inet-address value="${jboss.bind.address:127.0.0.1}"/<inet-address value="${jboss.bind.address:0.0.0.0}"/g' /opt/jboss/wildfly/standalone/configuration/standalone.xml && \
    sed -i 's/<inet-address value="${jboss.bind.address.management:127.0.0.1}"/<inet-address value="${jboss.bind.address.management:0.0.0.0}"/g' /opt/jboss/wildfly/standalone/configuration/standalone.xml

# Create deployments directory with correct permissions
RUN mkdir -p /opt/jboss/wildfly/standalone/deployments/ && \
    chown -R jboss:jboss /opt/jboss/wildfly/standalone/

# Copy WAR file
COPY --chown=jboss:jboss target/prover-jsf.war /opt/jboss/wildfly/standalone/deployments/

# Set environment variables with IPv4 configuration
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

# Expose ports
EXPOSE 8080 9990

# Set user
USER jboss

# Start WildFly with explicit IPv4 configuration
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djava.net.preferIPv4Stack=true"] 