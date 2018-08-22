FROM rmpestano/wildfly:13.0.0
MAINTAINER Rafael Pestano

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}


ENTRYPOINT ${WILDFLY_HOME}/bin/standalone-ee8.sh -b=0.0.0.0

