package com.lsoe.coolreader.exception;

/**
 * TODO: Describe purpose and behavior of CoolCSVException
 */
public class CoolReaderException extends Exception {

    public CoolReaderException() {
        this("Oh snap! Shit happened. Please report to soelynn007@gmail.com with full stacktrace.");
    }

    public CoolReaderException(String msg) {
        super(msg);
    }

}
