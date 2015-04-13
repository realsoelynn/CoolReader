package com.lsoe.coolcsv;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;

/**
 * TODO: Describe purpose and behavior of CoolCSVReader
 */
public class CoolCSVReader {

	private String csvFileURI;
	private CoolCSVColumn[] columns;

	private CoolCSVReader() {

	}

	public CoolCSVReader(String csvFileURI) {
		this(csvFileURI, null);
	}
	public CoolCSVReader(String csvFileURI, CoolCSVColumn[] columns) {

		this.csvFileURI = csvFileURI;
		this.columns = columns;
	}

	public <T> Object[][] readAllAsCustomObject(
			CoolConstructor... coolConstructors) throws Exception {

		ArrayList<Object[]> records = new ArrayList<Object[]>();
		CoolCSVRecord[] coolCSVRecords = readAllAsCoolCSVRecord();
		int columnIndex = 0;

		for (CoolCSVRecord coolCSVRecord : coolCSVRecords) {

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
		CoolCSVRecord[] coolCSVRecords = readAllAsCoolCSVRecord();

		for (CoolCSVRecord coolCSVRecord : coolCSVRecords) {

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
	public CoolCSVRecord[] readAllAsCoolCSVRecord() throws Exception {

		ArrayList<CoolCSVRecord> records = new ArrayList<CoolCSVRecord>();
		Reader reader = new FileReader(csvFileURI);
		CSVReader<String[]> csvReader = new CSVReaderBuilder<String[]>(reader)
				.entryParser(new DefaultCSVEntryParser())
				.strategy(CSVStrategy.UK_DEFAULT).build();

		boolean hasColumnRowSkipped = false;
		for (String[] csvReaderRecord : csvReader.readAll()) {

			if (!hasColumnRowSkipped) {
				hasColumnRowSkipped = true;
				continue;
			}

			CoolCSVRecord record = null;

			if (columns == null || columns.length < 0) {
				record = new CoolCSVRecord(csvReaderRecord);
			} else {
				record = new CoolCSVRecord(columns, csvReaderRecord);
			}

			records.add(record);
		}

		return records.toArray(new CoolCSVRecord[records.size()]);
	}

	public Object[][] readAll() throws Exception {

		CoolCSVRecord[] csvRecords = readAllAsCoolCSVRecord();
		ArrayList<Object[]> records = new ArrayList<Object[]>();

		for (CoolCSVRecord csvRecord : csvRecords) {

			ArrayList<Object> record = new ArrayList<Object>();
			for (CoolCSVColumn column : columns) {
				record.add(csvRecord.get(column.getColumnIndex(),
						column.getColumnType()));
			}

			records.add(record.toArray(new Object[record.size()]));
		}

		return records.toArray(new Object[records.size()][]);
	}
}
