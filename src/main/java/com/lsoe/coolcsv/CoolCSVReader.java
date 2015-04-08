package com.lsoe.coolcsv;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;
import com.lsoe.coolcsv.internal.CoolCSVColumn;

/**
 * TODO: Describe purpose and behavior of CoolCSVReader
 */
public class CoolCSVReader {

	private String csvFileURI;
	private CoolCSVColumn[] columns;

	private CoolCSVReader() {

	}

	public CoolCSVReader(String csvFileURI, CoolCSVColumn[] columns) {

		this.csvFileURI = csvFileURI;
		this.columns = columns;
	}

	public CoolCSVRecord[] readAll() throws Exception {

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
}
