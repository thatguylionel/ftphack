package de.tgl.comms;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import de.tgl.custom_exceptions.FlaggedCheckException;
import de.tgl.pojo.Credentials;
import de.tgl.util.Checks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("send")
public class ComsForwarding {
    private static final Logger LOGGER = Logger.getLogger(ComsForwarding.class.getName());

    /**
     * This method will send the associated file to an remote directory over SFTP
     *
     * @param fileName         {@link String} name of the file
     * @param sftpHostNameOrIP {@link String} This can be a name (ftp.something.com) or ip (10.0.0.1) of the server
     * @param sftpPortNumber   integer. The standard port number is 22. Leave empty if unknown.
     * @param sftpUserName     {@link String} Username of SFTP Server Account
     * @param sftpPassword     {@link String} Password of SFTP Server Account
     * @param targetDirectory  {@link String} The directory where the file is to be placed. Leave empty if unknown.
     * @return {@link String}
     */
    @GET
    @Path("/sftp")
    public String sendWithSFTP(@QueryParam("fileName") String fileName
            , @QueryParam("host") String sftpHostNameOrIP
            , @QueryParam("portNumber") int sftpPortNumber
            , @QueryParam("username") String sftpUserName
            , @QueryParam("password") String sftpPassword
            , @QueryParam("target") String targetDirectory
            , @QueryParam("destination") String destinationDirectory
    ) {
        boolean errorFlagged = false;
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        Credentials credentials = new Credentials(sftpHostNameOrIP, sftpPortNumber, sftpUserName, sftpPassword, targetDirectory, destinationDirectory, fileName);
        try {
            if (!Checks.isValid(credentials, true)) {
                throw new FlaggedCheckException(credentials.getMessage());
            }

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
            channelSftp.cd(credentials.getDestinationDirectory());

            //  Replace input filename with for loop, reading from directory
            //  https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
            File f = new File(credentials.getFileName());
            channelSftp.put(new FileInputStream(f), f.getName());

            //  move file to archive directory
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
        return credentials.getMessage().equals("") ? "Successfully sent" : credentials.getMessage();
    }
}
