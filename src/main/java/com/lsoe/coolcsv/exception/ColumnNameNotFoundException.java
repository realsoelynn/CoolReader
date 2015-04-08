package com.lsoe.coolcsv.exception;

/**
 * TODO: Describe purpose and behavior of ColumnNameNotFoundException
 */
public class ColumnNameNotFoundException extends CoolCSVException {

    public ColumnNameNotFoundException() {
        this("Invalid column name.");
    }

    public ColumnNameNotFoundException(String msg) {
        super(msg);
    }

}
