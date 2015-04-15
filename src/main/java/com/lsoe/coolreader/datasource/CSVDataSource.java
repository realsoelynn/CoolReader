package com.lsoe.coolreader.datasource;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;
import com.lsoe.coolreader.CoolColumn;
import com.lsoe.coolreader.CoolDataSource;
import com.lsoe.coolreader.CoolRecord;

/**
 * TODO: Describe purpose and behavior of CSVDataSource
 */
public class CSVDataSource extends CoolDataSource {

	private String csvFileURI;

	public CSVDataSource(String csvFileURI, CoolColumn[] columns) {
		super(columns);
		this.csvFileURI = csvFileURI;

	}

	public CoolRecord[] read() throws Exception {

		ArrayList<CoolRecord> records = new ArrayList<CoolRecord>();
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

			CoolRecord record = null;

			// Column Detail is not provided by user. So, default is set to
			// String for all columns
			if (columns == null || columns.length < 0) {

				columns = new CoolColumn[csvReaderRecord.length];
				for (int i = 0; i < columns.length; i++) {
					columns[i] = new CoolColumn(null, i, String.class);
				}
			}
			record = new CoolRecord(columns, csvReaderRecord);

			records.add(record);
		}

		return records.toArray(new CoolRecord[records.size()]);
	}
}
