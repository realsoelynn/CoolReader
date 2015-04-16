package com.lsoe.coolreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lsoe.coolreader.annotation.CoolConstructor;
import com.lsoe.coolreader.annotation.CoolConstructors;
import com.lsoe.coolreader.annotation.CoolData;
import com.lsoe.coolreader.test.enums.Country;
import com.lsoe.coolreader.test.models.User;

/**
 * TODO: Describe purpose and behavior of CoolReaderAnnotationTest
 */
public class CoolReaderAnnotationTest {

	public CoolReaderAnnotationTest() throws Exception {
		CoolReader.inject(this);
	}

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv", columns = {
			String.class, double.class, boolean.class, int.class, Country.class})
	public Object[][] data_1;
	@Test
	public void testCoolDataAnnotation() throws Exception {
		assertEquals(String.class, data_1[0][0].getClass());
		assertEquals(Double.class, data_1[0][1].getClass());
		assertEquals(Boolean.class, data_1[0][2].getClass());
		assertEquals(Integer.class, data_1[0][3].getClass());
		assertEquals(Country.class, data_1[0][4].getClass());

		String name = (String) data_1[0][0];
		double weight = (Double) data_1[0][1];
		boolean isMale = (Boolean) data_1[0][2];
		int height = (Integer) data_1[0][3];
		Country country = (Country) data_1[0][4];

		assertEquals("Soe Lynn", name);
		assertEquals(54.5, weight, 0);
		assertTrue("Expected isMale to be true.", isMale);
		assertEquals(188, height);
		assertEquals("Myanmar", country.getCountry());
	}

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv")
	@CoolConstructor(constructorClass = User.class, paramTypes = {String.class,
			double.class, boolean.class, int.class, Country.class})
	public Object[][] data_2;
	@Test
	public void testCoolConstructorAnnotation() throws Exception {

		User user = (User) data_2[0][0];

		assertEquals("Soe Lynn", user.getName());
		assertEquals(54.5, user.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user.isMale());
		assertEquals(188, user.getHeight());
		assertEquals(Country.MM, user.getCountry());
	}

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv")
	@CoolConstructors({
			@CoolConstructor(constructorClass = User.class, paramTypes = {
					String.class, double.class, boolean.class, int.class,
					Country.class}),
			@CoolConstructor(constructorClass = User.class, paramTypes = {
					String.class, double.class, boolean.class, int.class,
					Country.class})})
	public Object[][] data_3;
	@Test
	public void testCoolConstructorsAnnotation() throws Exception {

		User user = (User) data_3[0][0];
		User user2 = (User) data_3[0][1];

		assertEquals("Soe Lynn", user.getName());
		assertEquals(54.5, user.getWeight(), 0);
		assertTrue("Expected isMale to be true.", user.isMale());
		assertEquals(188, user.getHeight());
		assertEquals(Country.MM, user.getCountry());

		assertEquals("Xiao Zhu", user2.getName());
		assertEquals(51, user2.getWeight(), 0);
		assertFalse("Expected isMale to be false.", user2.isMale());
		assertEquals(170, user2.getHeight());
		assertEquals(Country.MM, user2.getCountry());
	}

}
