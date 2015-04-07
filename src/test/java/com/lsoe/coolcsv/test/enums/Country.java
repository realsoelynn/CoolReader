package com.lsoe.coolcsv.test.enums;

/**
 * TODO: Describe purpose and behavior of Country
 */
public enum Country {

    SG("Singapore"),
    MM("Myanmar"), ;

    private final String country;

    private Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

}
