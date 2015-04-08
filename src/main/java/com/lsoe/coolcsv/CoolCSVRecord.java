package com.lsoe.coolcsv;

import com.lsoe.coolcsv.exception.ColumnIndexNotValidException;
import com.lsoe.coolcsv.exception.ColumnNameNotFoundException;
import com.lsoe.coolcsv.exception.CoolCSVException;
import com.lsoe.coolcsv.internal.CoolCSVColumn;

/**
 * TODO: Describe purpose and behavior of CoolCSVRecord
 * Currently, it is only programmed for read-only mode
 * 
 */
public class CoolCSVRecord {

    private String[] record;
    private CoolCSVColumn[] columns;
    private boolean columnPropertiesDefined;

    public CoolCSVRecord(String[] record) {

        this.columns = null;
        this.record = record;
        this.columnPropertiesDefined = false;
    }

    public CoolCSVRecord(CoolCSVColumn[] columns, String[] record) {

        this.columns = columns;
        this.record = record;
        this.columnPropertiesDefined = true;
    }

    public boolean isColumnPropertiesDefined() {
        return columnPropertiesDefined;
    }

    public String get(String columnName) throws CoolCSVException {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return getRecord(columnIndex);
    }

    public String get(int columnIndex) throws Exception {

        return getRecord(columnIndex);
    }

    public <T> T get(String columnName, Class<T> columnTypeClass) throws CoolCSVException {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return get(columnIndex, columnTypeClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int columnIndex, Class<T> columnTypeClass) throws CoolCSVException {

        T result = null;
        if (columnTypeClass.isEnum()) {
            for (Object enumConstant : columnTypeClass.getEnumConstants()) {
                if (enumConstant.toString().equals(getRecord(columnIndex))) {
                    result = (T) enumConstant;
                    break;
                }
            }

            if (result == null) {
                throw new CoolCSVException(String.format("%s is not defined under the enum type %s.",
                        record[columnIndex], columnTypeClass.getName()));
            }
        } else {
            throw new CoolCSVException("Currently, only enum type is supported.");
        }

        return result;
    }

    public boolean getBoolean(String columnName) throws Exception {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return getBoolean(columnIndex);
    }

    public boolean getBoolean(int columnIndex) throws Exception {

        return Boolean.parseBoolean(getRecord(columnIndex));
    }

    public double getDouble(String columnName) throws Exception {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return getDouble(columnIndex);
    }

    public double getDouble(int columnIndex) throws Exception {

        return Double.parseDouble(getRecord(columnIndex));
    }

    public int getInt(String columnName) throws Exception {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return getInt(columnIndex);
    }

    public int getInt(int columnIndex) throws Exception {

        return Integer.parseInt(getRecord(columnIndex));
    }

    public long getLong(String columnName) throws Exception {

        int columnIndex = getColumnIndexInternalUse(columnName);

        return getLong(columnIndex);
    }

    public long getLong(int columnIndex) throws Exception {

        return Long.parseLong(getRecord(columnIndex));
    }

    public boolean has(String columnName) {

        boolean result = false;

        if (isColumnPropertiesDefined()) {
            for (CoolCSVColumn column : columns) {
                if (column.getColumnName().equals(columnName)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public int getColumnIndex(String columnName) {

        int index = -1;

        if (isColumnPropertiesDefined()) {
            for (int i = 0; i < columns.length; i++) {
                CoolCSVColumn column = columns[i];
                if (column.getColumnName().equals(columnName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    private int getColumnIndexInternalUse(String columnName) throws ColumnNameNotFoundException {

        int index = getColumnIndex(columnName);

        if (index < 0) {
            throw new ColumnNameNotFoundException(String.format("Column name \"%s\" is not valid.", columnName));
        }

        return index;
    }

    private String getRecord(int columnIndex) throws ColumnIndexNotValidException {
        checkForValidColumnIndex(columnIndex);

        return record[columnIndex];
    }

    private CoolCSVColumn getColumnProperty(int columnIndex) throws ColumnIndexNotValidException {
        checkForValidColumnIndex(columnIndex);
        CoolCSVColumn column = null;

        if (isColumnPropertiesDefined()) {
            column = columns[columnIndex];
        } else {
            column = new CoolCSVColumn(null, columnIndex, String.class.getName());
        }

        return column;
    }

    private void checkForValidColumnIndex(int columnIndex) throws ColumnIndexNotValidException {

        if (columnIndex < 0 || columnIndex >= record.length) {
            throw new ColumnIndexNotValidException(String.format(
                    "Invalid column index. Valid column index is from 0 to %d.", columns.length - 1));
        }
    }

}