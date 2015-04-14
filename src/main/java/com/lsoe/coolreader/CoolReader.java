package com.lsoe.coolreader;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.lsoe.coolreader.datasource.CSVDataSource;
import com.lsoe.coolreader.datasource.ObjectArrayDataSource;

/**
 * TODO: Describe purpose and behavior of CoolCSVReader
 */
public class CoolReader {

	private CoolDataSource datasource;

	private CoolReader() {
	}

	public CoolReader(String csvFileURI) {

		this(csvFileURI, null);
	}

	public CoolReader(String csvFileURI, CoolColumn[] columns) {
		datasource = new CSVDataSource(csvFileURI, columns);
	}

	public CoolReader(Object[][] data) {
		this(data, null);
	}

	public CoolReader(Object[][] data, CoolColumn[] columns) {
		datasource = new ObjectArrayDataSource(columns, data);
	}

	public CoolReader(CoolDataSource datasource) {
		this.datasource = datasource;
	}

	public CoolDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(CoolDataSource datasource) {
		this.datasource = datasource;
	}

	public <T> Object[][] readAllAsCustomObject(
			CoolConstructor... coolConstructors) throws Exception {

		ArrayList<Object[]> records = new ArrayList<Object[]>();
		CoolRecord[] coolCSVRecords = readAllAsCoolRecord();
		int columnIndex = 0;

		for (CoolRecord coolCSVRecord : coolCSVRecords) {

			ArrayList<Object> record = new ArrayList<Object>();

			for (int i = 0; i < coolConstructors.length; i++) {

				CoolConstructor coolConstructor = coolConstructors[i];
				Class<?> customObjectClass = coolConstructor
						.getConstructorClass();
				Class<?>[] paramsType = coolConstructor.getConstructorParams()
						.getParamsType();
				Constructor<?> constructor = customObjectClass
						.getConstructor(paramsType);
				ArrayList<Object> constructorParams = new ArrayList<Object>();

				for (int j = 0; j < paramsType.length; j++) {

					constructorParams.add(coolCSVRecord.get(columnIndex++,
							paramsType[j]));
				}

				record.add(constructor.newInstance(constructorParams.toArray()));
			}

			records.add(record.toArray());
		}

		return records.toArray(new Object[records.size()][]);
	}

	public <T> Object[][] readAllAsCustomObject(Class<T> customObjectClass,
			CoolConstructorParams parameterTypes) throws Exception {

		Class<?>[] paramsType = parameterTypes.getParamsType();

		ArrayList<Object[]> records = new ArrayList<Object[]>();
		Constructor<?> constructor = customObjectClass
				.getConstructor(paramsType);
		CoolRecord[] coolCSVRecords = readAllAsCoolRecord();

		for (CoolRecord coolCSVRecord : coolCSVRecords) {

			ArrayList<Object> record = new ArrayList<Object>();
			ArrayList<Object> constructorParams = new ArrayList<Object>();

			for (int i = 0; i < paramsType.length; i++) {

				constructorParams.add(coolCSVRecord.get(i, paramsType[i]));
			}

			record.add(constructor.newInstance(constructorParams.toArray()));
			records.add(record.toArray());
		}

		return records.toArray(new Object[records.size()][]);
	}

	public Object[][] readAll() throws Exception {

		CoolRecord[] csvRecords = readAllAsCoolRecord();
		ArrayList<Object[]> records = new ArrayList<Object[]>();

		for (CoolRecord csvRecord : csvRecords) {

			ArrayList<Object> record = new ArrayList<Object>();
			for (CoolColumn column : datasource.getColumns()) {
				record.add(csvRecord.get(column.getColumnIndex(),
						column.getColumnType()));
			}

			records.add(record.toArray(new Object[record.size()]));
		}

		return records.toArray(new Object[records.size()][]);
	}

	public CoolRecord[] readAllAsCoolRecord() throws Exception {

		return datasource.read();
	}
}
