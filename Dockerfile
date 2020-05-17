FROM rmpestano/wildfly:16.0.1
MAINTAINER Rafael Pestano

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}


ENTRYPOINT ${WILDFLY_HOME}/bin/standalone.sh -b=0.0.0.0

