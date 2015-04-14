package com.lsoe.coolreader;

import java.lang.reflect.Constructor;

import com.lsoe.coolreader.exception.ColumnIndexNotValidException;
import com.lsoe.coolreader.exception.ColumnNameNotFoundException;
import com.lsoe.coolreader.exception.CoolReaderException;

/**
 * TODO: Describe purpose and behavior of CoolCSVRecord Currently, it is only
 * programmed for read-only mode
 * 
 */
public class CoolRecord {

	private Object[] record;
	private CoolColumn[] columns;
	private boolean columnPropertiesDefined;

	public CoolRecord(Object[] record) {

		this.columns = null;
		this.record = record;
		this.columnPropertiesDefined = false;
	}

	public CoolRecord(CoolColumn[] columns, Object[] record) {

		this.columns = columns;
		this.record = record;
		this.columnPropertiesDefined = true;
	}

	public boolean isColumnPropertiesDefined() {
		return columnPropertiesDefined;
	}

	public Object get(String columnName) throws CoolReaderException {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getRecord(columnIndex);
	}

	public Object get(int columnIndex) throws Exception {

		return getRecord(columnIndex);
	}

	// TODO:
	public <T> T get(String columnName, Class<T> columnTypeClass)
			throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return get(columnIndex, columnTypeClass);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(int columnIndex, Class<T> columnTypeClass)
			throws Exception {

		T result = null;
		Object data = get(columnIndex);

		if (data.getClass().equals(String.class)) {

			if (columnTypeClass.isEnum()) {
				for (Object enumConstant : columnTypeClass.getEnumConstants()) {
					if (enumConstant.toString().equals(getRecord(columnIndex))) {
						result = (T) enumConstant;
						break;
					}
				}

				if (result == null) {
					throw new CoolReaderException(String.format(
							"%s is not defined under the enum type %s.",
							record[columnIndex], columnTypeClass.getName()));
				}
			} else if (columnTypeClass.getName().equals(String.class.getName())) {
				result = (T) get(columnIndex);
			} else if (columnTypeClass.getName()
					.equals(Boolean.class.getName())) {
				result = (T) getBoolean(columnIndex);
			} else if (columnTypeClass.getName()
					.equals(Integer.class.getName())) {
				result = (T) getInt(columnIndex);
			} else if (columnTypeClass.getName().equals(Double.class.getName())) {
				result = (T) getDouble(columnIndex);
			} else if (columnTypeClass.getName().equals(Long.class.getName())) {
				result = (T) getLong(columnIndex);
			} else if (columnTypeClass.getName()
					.equals(boolean.class.getName())) {
				result = (T) getBoolean(columnIndex);
			} else if (columnTypeClass.getName().equals(int.class.getName())) {
				result = (T) getInt(columnIndex);
			} else if (columnTypeClass.getName().equals(double.class.getName())) {
				result = (T) getDouble(columnIndex);
			} else if (columnTypeClass.getName().equals(long.class.getName())) {
				result = (T) getLong(columnIndex);
			} else { // This case is assume that the custom type provided have a
						// constructor with one String-type parameter
				Constructor<T> constructor = columnTypeClass
						.getConstructor(String.class);
				result = constructor.newInstance(get(columnIndex));
			}
		} else {
			result = (T) get(columnIndex);
		}

		return result;
	}

	public Boolean getBoolean(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getBoolean(columnIndex);
	}

	public Boolean getBoolean(int columnIndex) throws Exception {

		return Boolean.parseBoolean(getString(columnIndex));
	}

	public Double getDouble(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getDouble(columnIndex);
	}

	public Double getDouble(int columnIndex) throws Exception {

		return Double.parseDouble(getString(columnIndex));
	}

	public Integer getInt(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getInt(columnIndex);
	}

	public Integer getInt(int columnIndex) throws Exception {

		return Integer.parseInt(getString(columnIndex));
	}

	public Long getLong(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getLong(columnIndex);
	}

	public Long getLong(int columnIndex) throws Exception {

		return Long.parseLong(getString(columnIndex));
	}

	public String getString(String columnName) throws Exception {

		int columnIndex = getColumnIndexInternalUse(columnName);

		return getString(columnIndex);
	}

	public String getString(int columnIndex) throws Exception {

		return get(columnIndex).toString();
	}

	public boolean has(String columnName) {

		boolean result = false;

		if (isColumnPropertiesDefined()) {
			for (CoolColumn column : columns) {
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
				CoolColumn column = columns[i];
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

	private Object getRecord(int columnIndex)
			throws ColumnIndexNotValidException {
		checkForValidColumnIndex(columnIndex);

		return record[columnIndex];
	}

	private CoolColumn getColumnProperty(int columnIndex)
			throws ColumnIndexNotValidException {
		checkForValidColumnIndex(columnIndex);
		CoolColumn column = null;

		if (isColumnPropertiesDefined()) {
			column = columns[columnIndex];
		} else {
			column = new CoolColumn(null, columnIndex);
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