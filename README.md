#ABOUT
This project example is to copy a file to an SFTP server, and to also download FILES from an SFTP server. 

I did not put too much effort into it, with regards to commenting, but I did put in some guidelines on how the methods work. the main classes to start work is in the de.tgl.comms package, with both ComsForwarding and ComsReceiving.  

# Build
mvn clean package && docker build -t de.intime/ftp-hack .

# THIS PROJECT CAN RUN ON DOCKER AS WELL
docker rm -f ftp-hack || true && docker run -d -p 8080:8080 -p 4848:4848 --name ftp-hack de.intime/ftp-hack

#PING URL TEST
http://localhost:8080/ftp-hack/resources/ping

# Example URL
http://localhost:8080/ftp-hack/resources/send/sftp?destination=DESTPLACE&host=SOME.SFTP.SERVER&username=SFTP_USERNAMEt&password=SFTP_PASSWORD&portNumber=22&fileName=LOCATION_AND_FILENAME
http://localhost:8080/ftp-hack/resources/receive/sftp?target=WHERE_TO_COPY_FROM&host=SOME.SFTP.SERVER&username=SFTP_USERNAMEt&password=SFTP_PASSWORD&portNumber=22&destination=WHERE_IT_MUST_GO&fileType=FILE_EXTENSION
 