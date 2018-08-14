FROM rmpestano/wildfly:13.0.0
MAINTAINER Rafael Pestano

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}
