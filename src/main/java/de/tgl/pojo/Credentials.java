package de.tgl.pojo;

public class Credentials {
    private String fileName;
    private String sftpHostNameOrIP;
    private int sftpPortNumber;
    private String sftpUserName;
    private String sftpPassword;
    private String targetDirectory;
    private String destinationDirectory;
    private String message;

    public Credentials() {
    }

    public Credentials(String sftpHostNameOrIP, int sftpPortNumber, String sftpUserName, String sftpPassword, String targetDirectory, String destinationDirectory) {
        this.sftpHostNameOrIP = sftpHostNameOrIP;
        this.sftpPortNumber = sftpPortNumber;
        this.sftpUserName = sftpUserName;
        this.sftpPassword = sftpPassword;
        this.targetDirectory = targetDirectory;
        this.destinationDirectory = destinationDirectory;
        this.message = "";
    }

    public Credentials(String sftpHostNameOrIP, int sftpPortNumber, String sftpUserName, String sftpPassword, String targetDirectory, String destinationDirectory, String fileName) {
        this(sftpHostNameOrIP, sftpPortNumber, sftpUserName, sftpPassword, targetDirectory, destinationDirectory);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSftpHostNameOrIP() {
        return sftpHostNameOrIP;
    }

    public void setSftpHostNameOrIP(String sftpHostNameOrIP) {
        this.sftpHostNameOrIP = sftpHostNameOrIP;
    }

    public int getSftpPortNumber() {
        return sftpPortNumber;
    }

    public void setSftpPortNumber(int sftpPortNumber) {
        this.sftpPortNumber = sftpPortNumber;
    }

    public String getSftpUserName() {
        return sftpUserName;
    }

    public void setSftpUserName(String sftpUserName) {
        this.sftpUserName = sftpUserName;
    }

    public String getSftpPassword() {
        return sftpPassword;
    }

    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
