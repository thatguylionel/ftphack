FROM airhacks/glassfish
COPY ./target/ftp-hack.war ${DEPLOYMENT_DIR}
