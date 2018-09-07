package de.tgl.custom_exceptions;

public class FlaggedCheckException extends Exception  {
    public FlaggedCheckException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
