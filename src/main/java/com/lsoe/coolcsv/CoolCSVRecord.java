package com.lsoe.coolcsv;

import java.util.List;

import com.lsoe.coolcsv.internal.CoolCSVColumn;

/**
 * TODO: Describe purpose and behavior of CoolCSVRecord
 */
public class CoolCSVRecord {

    private List<String> record;
    private List<CoolCSVColumn> columns;

    public CoolCSVRecord(List<CoolCSVColumn> columns) {
        this.columns = columns;
    }
}
