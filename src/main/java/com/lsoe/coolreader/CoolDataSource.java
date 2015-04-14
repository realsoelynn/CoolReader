package com.lsoe.coolreader;

/**
 * TODO: Describe purpose and behavior of CoolDataSource
 */
public abstract class CoolDataSource {

	public CoolColumn[] columns;

	private CoolDataSource() {

	}

	public CoolDataSource(CoolColumn[] columns) {
		this.columns = columns;
	}

	public CoolColumn[] getColumns() {
		return columns;
	}

	public void setColumns(CoolColumn[] columns) {
		this.columns = columns;
	}

	public abstract CoolRecord[] read() throws Exception;

}
