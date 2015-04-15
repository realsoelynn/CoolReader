package com.lsoe.coolreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lsoe.coolreader.datasource.ObjectArrayDataSource;
import com.lsoe.coolreader.test.enums.Country;
import com.lsoe.coolreader.test.models.User;

/**
 * TODO: Describe purpose and behavior of CoolCSVReaderTest
 */
public class CoolReaderTest {

	@Test
	public void testReadAllAsCoolRecord() throws Exception {

		String csvFileURI = "src/test/resources/CSV/PersonInfo.csv";
		CoolColumn[] columns = new CoolColumn[]{new CoolColumn("name", 0),
				new CoolColumn("weight", 1), new CoolColumn("isMale", 2),
				new CoolColumn("height", 3), new CoolColumn("country", 4)};
		CoolReader csvReader = new CoolReader(csvFileURI, columns);

		CoolRecord record = csvReader.readAllAsCoolRecord()[0];

		String name = record.getString("name");
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

	@Test
	public void testReadAllAsObjectArrays() throws Exception {

		String csvFileURI = "src/test/resources/CSV/PersonInfo.csv";
		CoolColumn[] columns = new CoolColumn[]{
				new CoolColumn("name", 0, String.class),
				new CoolColumn("weight", 1, Double.class),
				new CoolColumn("isMale", 2, Boolean.class),
				new CoolColumn("height", 3, Integer.class),
				new CoolColumn("country", 4, Country.class)};
		CoolReader csvReader = new CoolReader(csvFileURI, columns);

		Object[][] records = csvReader.readAll();

		String name = (String) records[0][0];
		double weight = (Double) records[0][1];
		boolean isMale = (Boolean) records[0][2];
		int height = (Integer) records[0][3];
		Country country = (Country) records[0][4];

		assertEquals("Soe Lynn", name);
		assertEquals(54.5, weight, 0);
		assertTrue("Expected isMale to be true.", isMale);
		assertEquals(188, height);
		assertEquals("Myanmar", country.getCountry());
	}

	@Test
	public void testReadAllAsCustomObject() throws Exception {
		String csvFileURI = "src/test/resources/CSV/PersonInfo.csv";
		CoolReader csvReader = new CoolReader(csvFileURI);

		User user = (User) csvReader.readAllAsCustomObject(User.class,
				new CoolConstructorParams(String.class, double.class,
						boolean.class, int.class, Country.class))[0][0];

		assertEquals("Soe Lynn", user.getName());
		assertEquals(54.5, user.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user.isMale());
		assertEquals(188, user.getHeight());
		assertEquals(Country.MM, user.getCountry());
	}

	@Test
	public void testReadAllAsCustomObjectUsingCoolConstrutor() throws Exception {
		String csvFileURI = "src/test/resources/CSV/PersonInfo.csv";
		CoolReader csvReader = new CoolReader(csvFileURI);

		Object[] users = csvReader.readAllAsCustomObject(
				new CoolConstructor(User.class, new CoolConstructorParams(
						String.class, double.class, boolean.class, int.class,
						Country.class)), new CoolConstructor(User.class,
						new CoolConstructorParams(String.class, double.class,
								boolean.class, int.class, Country.class)))[0];

		User user1 = (User) users[0];
		User user2 = (User) users[1];

		assertEquals("Soe Lynn", user1.getName());
		assertEquals(54.5, user1.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user1.isMale());
		assertEquals(188, user1.getHeight());
		assertEquals(Country.MM, user1.getCountry());

		assertEquals("Xiao Zhu", user2.getName());
		assertEquals(51, user2.getWeight(), 0);
		assertFalse("Expected isMale to be false.", user2.isMale());
		assertEquals(170, user2.getHeight());
		assertEquals(Country.MM, user2.getCountry());
	}

	@Test
	public void testObjectArrayDataSourceReadAllAsCustomObject()
			throws Exception {

		Object[][] data = new Object[][]{

		new Object[]{"Soe Lynn", 54.5, true, 188, Country.MM}

		};

		CoolReader reader = new CoolReader(data);

		User user = (User) reader.readAllAsCustomObject(User.class,
				new CoolConstructorParams(String.class, double.class,
						boolean.class, int.class, Country.class))[0][0];

		assertEquals("Soe Lynn", user.getName());
		assertEquals(54.5, user.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user.isMale());
		assertEquals(188, user.getHeight());
		assertEquals(Country.MM, user.getCountry());
	}

	@Test
	public void testObjectArrayDataSourceReadAllAsCustomObjectUsingCoolConstructor()
			throws Exception {

		Object[][] data = new Object[][]{

		new Object[]{"Soe Lynn", 54.5, true, 188, Country.MM, "Xiao Zhu", 51,
				false, 170, Country.MM}

		};

		CoolReader reader = new CoolReader(data);

		Object[] users = reader.readAllAsCustomObject(
				new CoolConstructor(User.class, new CoolConstructorParams(
						String.class, double.class, boolean.class, int.class,
						Country.class)), new CoolConstructor(User.class,
						new CoolConstructorParams(String.class, double.class,
								boolean.class, int.class, Country.class)))[0];

		User user1 = (User) users[0];
		User user2 = (User) users[1];

		assertEquals("Soe Lynn", user1.getName());
		assertEquals(54.5, user1.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user1.isMale());
		assertEquals(188, user1.getHeight());
		assertEquals(Country.MM, user1.getCountry());

		assertEquals("Xiao Zhu", user2.getName());
		assertEquals(51, user2.getWeight(), 0);
		assertFalse("Expected isMale to be false.", user2.isMale());
		assertEquals(170, user2.getHeight());
		assertEquals(Country.MM, user2.getCountry());
	}

	@Test
	public void testObjectArrayDataSourceReadAll() throws Exception {

		Object[][] data = new Object[][]{

		new Object[]{"Soe Lynn", 54.5, true, 188, Country.MM}

		};

		CoolReader reader = new CoolReader(data);

		Object[][] records = reader.readAll();

		assertEquals(String.class, records[0][0].getClass());
		assertEquals(Double.class, records[0][1].getClass());
		assertEquals(Boolean.class, records[0][2].getClass());
		assertEquals(Integer.class, records[0][3].getClass());
		assertEquals(Country.class, records[0][4].getClass());
	}

	@Test
	public void testReadFunctionWithCustomDataSource() throws Exception {

		Object[][] data = new Object[][]{

		new Object[]{"Soe Lynn", 54.5, true, 188, Country.MM}

		};
		ObjectArrayDataSource datasource = new ObjectArrayDataSource(data);
		CoolReader reader = new CoolReader(datasource);

		Object[][] records = reader.readAll();

		assertEquals(String.class, records[0][0].getClass());
		assertEquals(Double.class, records[0][1].getClass());
		assertEquals(Boolean.class, records[0][2].getClass());
		assertEquals(Integer.class, records[0][3].getClass());
		assertEquals(Country.class, records[0][4].getClass());
	}

}