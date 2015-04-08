package com.lsoe.coolcsv;

/**
 * TODO: Describe purpose and behavior of CoolCSVColumn
 */
public class CoolCSVColumn {

	private String columnName;
	private int columnIndex;
	private Class<?> columnType; // must be java full qualifier class name

	private CoolCSVColumn() {
	}

	public CoolCSVColumn(String columnName, int columnIndex) {
		this(columnName, columnIndex, String.class);
	}

	public CoolCSVColumn(String columnName, int columnIndex,
			Class<?> columnTypeClass) {
		this.columnName = columnName;
		this.columnIndex = columnIndex;
		this.columnType = columnTypeClass;
	}

	public String getColumnName() {
		return columnName;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public Class<?> getColumnType() {
		return columnType;
	}

}