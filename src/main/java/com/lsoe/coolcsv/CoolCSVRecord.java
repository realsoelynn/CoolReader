package com.lsoe.coolcsv;

import com.lsoe.coolcsv.internal.CoolCSVColumn;

/**
 * TODO: Describe purpose and behavior of CoolCSVRecord
 */
public class CoolCSVRecord {

    private String[] record;
    private CoolCSVColumn[] columns;

    public CoolCSVRecord(CoolCSVColumn[] columns, String[] record) {
        this.columns = columns;
        this.record = record;
    }

    public Object get(String columnName) throws Exception {
        for (CoolCSVColumn column : columns) {
            if (column.getColumnName().equals(columnName)) {
                return get(column.getColumnIndex());
            }
        }

        throw new Exception(String.format("Column name \"%s\" is not found.", columnName));
    }

    public Object get(int columnIndex) throws Exception {

        if (columnIndex < 0 || columnIndex >= columns.length) {
            return null;
        }

        Object result = null;
        CoolCSVColumn column = columns[columnIndex];

        Class<?> columnTypeClass = Class.forName(column.getColumnType());

        if (columnTypeClass.isEnum()) {
            for (Object enumConstant : columnTypeClass.getEnumConstants()) {
                if (enumConstant.toString().equals(record[columnIndex])) {
                    result = enumConstant;
                    break;
                }
            }

            if (result == null) {
                throw new Exception(String.format("%s is not defined under the enum type %s.", record[columnIndex],
                        column.getColumnType()));
            }
        } else {
            result = record[columnIndex];
        }

        return result;
    }
}