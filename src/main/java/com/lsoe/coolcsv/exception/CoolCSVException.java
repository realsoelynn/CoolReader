package com.lsoe.coolcsv.exception;

/**
 * TODO: Describe purpose and behavior of CoolCSVException
 */
public class CoolCSVException extends Exception {

    public CoolCSVException() {
        this("Oh snap! Shit happened. Please report to soelynn007@gmail.com with full stacktrace.");
    }

    public CoolCSVException(String msg) {
        super(msg);
    }

}
