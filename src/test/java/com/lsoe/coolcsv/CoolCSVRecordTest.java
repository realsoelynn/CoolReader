package com.lsoe.coolcsv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lsoe.coolcsv.exception.ColumnNameNotFoundException;
import com.lsoe.coolcsv.test.enums.Country;

public class CoolCSVRecordTest {

	@Test(expected = ColumnNameNotFoundException.class)
	public void testRecordWithoutColumnPropertiesSpecifiedScenario()
			throws Exception {

		String[] record = new String[]{"Soe Lynn", "54.5", "true", "188", "MM"};

		CoolCSVRecord coolRecord = new CoolCSVRecord(record);
		String name = coolRecord.get(0);
		double weight = coolRecord.getDouble(1);
		boolean isMale = coolRecord.getBoolean(2);
		int height = coolRecord.getInt(3);
		Country country = coolRecord.get(4, Country.class);

		assertEquals("Soe Lynn", name);
		assertEquals(54.5, weight, 0);
		assertTrue("Expected isMale to be true.", isMale);
		assertEquals(188, height);
		assertEquals("Myanmar", country.getCountry());
		assertFalse("Column \"name\" is expected to be not exists.",
				coolRecord.has("name"));

		coolRecord.get("name");
	}

	@Test
	public void testRecordWithColumnPropertiesSpecifiedScenario()
			throws Exception {

		CoolCSVColumn[] columns = new CoolCSVColumn[]{
				new CoolCSVColumn("name", 0), new CoolCSVColumn("weight", 1),
				new CoolCSVColumn("isMale", 2), new CoolCSVColumn("height", 3),
				new CoolCSVColumn("country", 4)};
		String[] record = new String[]{"Soe Lynn", "54.5", "true", "188", "MM"};

		CoolCSVRecord coolRecord = new CoolCSVRecord(columns, record);
		String name = coolRecord.get("name");
		double weight = coolRecord.getDouble("weight");
		boolean isMale = coolRecord.getBoolean("isMale");
		int height = coolRecord.getInt("height");
		Country country = coolRecord.get("country", Country.class);

		assertEquals("Soe Lynn", name);
		assertEquals(54.5, weight, 0);
		assertTrue("Expected isMale to be true.", isMale);
		assertEquals(188, height);
		assertEquals("Myanmar", country.getCountry());
		assertTrue("Column \"name\" is expected to be exists.",
				coolRecord.has("name"));
	}

}
