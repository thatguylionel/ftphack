package de.tgl.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import de.tgl.pojo.Credentials;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileManagement {
    /**
     * Generates the directory
     *
     * @param directory Name of directory
     */
    static void generateDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    /**
     * This method will move the next file in the queue to the location in question
     *
     * @param channelSftp {@link ChannelSftp}
     * @param credentials {@link Credentials}
     * @param entry       {@link com.jcraft.jsch.ChannelSftp.LsEntry}
     * @throws SftpException error
     * @throws IOException   error
     */
    public static void moveFile(ChannelSftp channelSftp, Credentials credentials, ChannelSftp.LsEntry entry) throws SftpException, IOException {
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(channelSftp.get(entry.getFilename()));
        File newFile = new File(credentials.getDestinationDirectory() + entry.getFilename());
        OutputStream os = new FileOutputStream(newFile);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int readCount;
        while ((readCount = bis.read(buffer)) > 0) {
            bos.write(buffer, 0, readCount);
        }
        bis.close();
        bos.close();
    }
}
