package de.tgl.util;

import de.tgl.pojo.Credentials;

public class Checks {
    /**
     * Standard checks to ensure all data is correct
     *
     * @param credentials {@link Credentials}
     * @return boolean if validation was successful.
     */
    public static boolean isValid(Credentials credentials, boolean isFileCheckRequired) {
        if (credentials.getSftpHostNameOrIP() == null || credentials.getSftpHostNameOrIP().trim().equals("")) {
            credentials.setMessage("No Host FTP Name/IP was provided...");
            return false;
        }
        if (credentials.getSftpPortNumber() == 0) {
            credentials.setSftpPortNumber(22);
        }
        if (credentials.getSftpUserName() == null || credentials.getSftpUserName().trim().equals("")) {
            credentials.setMessage("No sftpUserName was provided...");
            return false;
        }
        if (credentials.getSftpPassword() == null || credentials.getSftpPassword().trim().equals("")) {
            credentials.setMessage("No sftpPassword was provided...");
            return false;
        }
        if (credentials.getTargetDirectory() == null || credentials.getTargetDirectory().isEmpty()) {
            credentials.setTargetDirectory("/");
            return false;
        }

        if (isFileCheckRequired && (credentials.getFileName() == null || credentials.getFileName().trim().isEmpty())) {
            credentials.setMessage("Please proved a file to continue...");
            return false;
        }

        return true;
    }
}
