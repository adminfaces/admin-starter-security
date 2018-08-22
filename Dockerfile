FROM rmpestano/wildfly:13.0.0
MAINTAINER Rafael Pestano

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}


ENTRYPOINT ${WILDFLY_HOME}/bin/standalone.sh -b=0.0.0.0  --server-config=standalone-ee8.xml

