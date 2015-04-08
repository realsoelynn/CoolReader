package com.lsoe.coolcsv.internal;

/**
 * TODO: Describe purpose and behavior of CoolCSVColumn
 */
public class CoolCSVColumn {

    private String columnName;
    private int columnIndex;
    private String columnType; // must be java full qualifier class name

    private CoolCSVColumn() {
    }

    public CoolCSVColumn(String columnName, int columnIndex) {
        this(columnName, columnIndex, null);
    }

    public CoolCSVColumn(String columnName, int columnIndex, String columnType) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

}