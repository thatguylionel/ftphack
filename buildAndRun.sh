#!/bin/sh
mvn clean package && docker build -t de.intime/ftp-hack .
docker rm -f ftp-hack || true && docker run -d -p 8080:8080 -p 4848:4848 --name ftp-hack de.intime/ftp-hack 
