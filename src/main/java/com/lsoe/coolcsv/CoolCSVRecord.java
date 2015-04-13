package com.lsoe.coolcsv;

import com.lsoe.coolcsv.exception.ColumnIndexNotValidException;
import com.lsoe.coolcsv.exception.ColumnNameNotFoundException;
import com.lsoe.coolcsv.exception.CoolCSVException;

/**
 * TODO: Describe purpose and behavior of CoolCSVRecord Currently, it is only
 * programmed for read-only mode
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

	public <T> T get(String columnName, Class<T> columnTypeClass)
			throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return get(columnIndex, columnTypeClass);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(int columnIndex, Class<T> columnTypeClass)
			throws Exception {

		T result = null;
		if (columnTypeClass.isEnum()) {
			for (Object enumConstant : columnTypeClass.getEnumConstants()) {
				if (enumConstant.toString().equals(getRecord(columnIndex))) {
					result = (T) enumConstant;
					break;
				}
			}

			if (result == null) {
				throw new CoolCSVException(String.format(
						"%s is not defined under the enum type %s.",
						record[columnIndex], columnTypeClass.getName()));
			}
		} else if (columnTypeClass.getName().equals(String.class.getName())) {
			result = (T) get(columnIndex);
		} else if (columnTypeClass.getName().equals(Boolean.class.getName())) {
			result = (T) getBoolean(columnIndex);
		} else if (columnTypeClass.getName().equals(Integer.class.getName())) {
			result = (T) getInt(columnIndex);
		} else if (columnTypeClass.getName().equals(Double.class.getName())) {
			result = (T) getDouble(columnIndex);
		} else if (columnTypeClass.getName().equals(Long.class.getName())) {
			result = (T) getLong(columnIndex);
		} else if (columnTypeClass.getName().equals(boolean.class.getName())) {
			result = (T) getBoolean(columnIndex);
		} else if (columnTypeClass.getName().equals(int.class.getName())) {
			result = (T) getInt(columnIndex);
		} else if (columnTypeClass.getName().equals(double.class.getName())) {
			result = (T) getDouble(columnIndex);
		} else if (columnTypeClass.getName().equals(long.class.getName())) {
			result = (T) getLong(columnIndex);
		} else {
			throw new CoolCSVException(
					"Currently, only enum and built-in data types { String, Double, Integer, Booelan, Double, Long} are supported.");
		}

		return result;
	}

	public Boolean getBoolean(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getBoolean(columnIndex);
	}

	public Boolean getBoolean(int columnIndex) throws Exception {

		return Boolean.parseBoolean(getRecord(columnIndex));
	}

	public Double getDouble(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getDouble(columnIndex);
	}

	public Double getDouble(int columnIndex) throws Exception {

		return Double.parseDouble(getRecord(columnIndex));
	}

	public Integer getInt(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getInt(columnIndex);
	}

	public Integer getInt(int columnIndex) throws Exception {

		return Integer.parseInt(getRecord(columnIndex));
	}

	public Long getLong(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getLong(columnIndex);
	}

	public Long getLong(int columnIndex) throws Exception {

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

	private int getColumnIndexInternalUse(String columnName)
			throws ColumnNameNotFoundException {

		int index = getColumnIndex(columnName);

		if (index < 0) {
			throw new ColumnNameNotFoundException(String.format(
					"Column name \"%s\" is not valid.", columnName));
		}

		return index;
	}

	private String getRecord(int columnIndex)
			throws ColumnIndexNotValidException {
		checkForValidColumnIndex(columnIndex);

		return record[columnIndex];
	}

	private CoolCSVColumn getColumnProperty(int columnIndex)
			throws ColumnIndexNotValidException {
		checkForValidColumnIndex(columnIndex);
		CoolCSVColumn column = null;

		if (isColumnPropertiesDefined()) {
			column = columns[columnIndex];
		} else {
			column = new CoolCSVColumn(null, columnIndex);
		}

		return column;
	}

	private void checkForValidColumnIndex(int columnIndex)
			throws ColumnIndexNotValidException {

		if (columnIndex < 0 || columnIndex >= record.length) {
			throw new ColumnIndexNotValidException(
					String.format(
							"Invalid column index. Valid column index is from 0 to %d.",
							columns.length - 1));
		}
	}

}