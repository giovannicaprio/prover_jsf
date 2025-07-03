FROM jboss/wildfly:10.0.0.Final

# Set environment variables
ENV WILDFLY_USER=admin
ENV WILDFLY_PASS=admin123

# Add WildFly admin user
RUN /opt/jboss/wildfly/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

# Configure WildFly to bind to all interfaces
RUN sed -i 's/<inet-address value="${jboss.bind.address:127.0.0.1}"/<inet-address value="${jboss.bind.address:0.0.0.0}"/g' /opt/jboss/wildfly/standalone/configuration/standalone.xml
RUN sed -i 's/<inet-address value="${jboss.bind.address.management:127.0.0.1}"/<inet-address value="${jboss.bind.address.management:0.0.0.0}"/g' /opt/jboss/wildfly/standalone/configuration/standalone.xml

# Expose ports
EXPOSE 8080 9990

# Set the default command to run on boot
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"] 