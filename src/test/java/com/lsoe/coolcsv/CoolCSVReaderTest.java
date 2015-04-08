package com.lsoe.coolcsv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lsoe.coolcsv.internal.CoolCSVColumn;
import com.lsoe.coolcsv.test.enums.Country;

/**
 * TODO: Describe purpose and behavior of CoolCSVReaderTest
 */
public class CoolCSVReaderTest {

	@Test
	public void test() throws Exception {

		String csvFileURI = "/Users/lsoe/Documents/workspace-sts-3.6.3.RELEASE/CoolCSV/src/test/resources/CSV/PersonInfo.csv";
		CoolCSVColumn[] columns = new CoolCSVColumn[]{
				new CoolCSVColumn("name", 0), new CoolCSVColumn("weight", 1),
				new CoolCSVColumn("isMale", 2), new CoolCSVColumn("height", 3),
				new CoolCSVColumn("country", 4)};
		CoolCSVReader csvReader = new CoolCSVReader(csvFileURI, columns);

		CoolCSVRecord record = csvReader.readAll()[0];

		String name = record.get("name");
		double weight = record.getDouble("weight");
		boolean isMale = record.getBoolean("isMale");
		int height = record.getInt("height");
		Country country = record.get("country", Country.class);

		assertEquals("Soe Lynn", name);
		assertEquals(54.5, weight, 0);
		assertTrue("Expected isMale to be true.", isMale);
		assertEquals(188, height);
		assertEquals("Myanmar", country.getCountry());
	}

}
