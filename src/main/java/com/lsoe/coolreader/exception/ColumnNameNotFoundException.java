package com.lsoe.coolreader.exception;

/**
 * TODO: Describe purpose and behavior of ColumnNameNotFoundException
 */
public class ColumnNameNotFoundException extends CoolReaderException {

    public ColumnNameNotFoundException() {
        this("Invalid column name.");
    }

    public ColumnNameNotFoundException(String msg) {
        super(msg);
    }

}
