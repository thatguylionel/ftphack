package de.tgl.comms;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import de.tgl.pojo.Credentials;
import de.tgl.util.FileManagement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("receive")
public class ComsReceiving {
    private static final Logger LOGGER = Logger.getLogger(ComsReceiving.class.getName());

    /**
     * @param sftpHostNameOrIP     {@link String} This can be a name (ftp.something.com) or ip (10.0.0.1) of the server
     * @param sftpPortNumber       integer. The standard port number is 22. Leave empty if unknown.
     * @param sftpUserName         {@link String} Username of SFTP Server Account
     * @param sftpPassword         {@link String} Password of SFTP Server Account
     * @param targetDirectory      {@link String} The directory where the files needs to be fetched from
     * @param destinationDirectory {@link String} The directory where the files will be downloaded to
     * @param fileType             {@link String} The file extension. For example "xml", "csv", "pdf", etc...
     * @return {@link String}
     */
    @GET
    @Path("/sftp")
    public String allFilesWithSFTP(
            @QueryParam("host") String sftpHostNameOrIP
            , @QueryParam("portNumber") int sftpPortNumber
            , @QueryParam("username") String sftpUserName
            , @QueryParam("password") String sftpPassword
            , @QueryParam("target") String targetDirectory
            , @QueryParam("destination") String destinationDirectory
            , @QueryParam("fileType") String fileType
    ) {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Credentials credentials = new Credentials(sftpHostNameOrIP, sftpPortNumber, sftpUserName, sftpPassword, targetDirectory, destinationDirectory + "\\" + date + "\\", fileType);
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(credentials.getSftpUserName(), credentials.getSftpHostNameOrIP(), credentials.getSftpPortNumber());
            session.setPassword(credentials.getSftpPassword());
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(credentials.getTargetDirectory());

            Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*." + credentials.getFileName());
            for (ChannelSftp.LsEntry entry : list) {
                FileManagement.moveFile(channelSftp, credentials, entry);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, "-------------------------------------------------------------------------------");
            ex.printStackTrace();
            if (credentials.getMessage().equals("")) {
                credentials.setMessage(ex.getMessage());
            }
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return credentials.getMessage().equals("") ? "Operation Completed" : credentials.getMessage();
    }
}
