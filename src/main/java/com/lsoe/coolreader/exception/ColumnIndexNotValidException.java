package com.lsoe.coolreader.exception;

/**
 * TODO: Describe purpose and behavior of ColumnIndexNotValidException
 */
public class ColumnIndexNotValidException extends CoolReaderException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ColumnIndexNotValidException() {
        this("Invalid column index.");
    }

    public ColumnIndexNotValidException(String msg) {
        super(msg);
    }

}
