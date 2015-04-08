package com.lsoe.coolcsv.exception;

/**
 * TODO: Describe purpose and behavior of ColumnIndexNotValidException
 */
public class ColumnIndexNotValidException extends CoolCSVException {

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
