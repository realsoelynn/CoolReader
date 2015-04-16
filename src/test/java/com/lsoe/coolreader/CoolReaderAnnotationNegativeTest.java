package com.lsoe.coolreader;

import org.junit.Test;

import com.lsoe.coolreader.annotation.CoolConstructor;
import com.lsoe.coolreader.annotation.CoolData;
import com.lsoe.coolreader.exception.CoolReaderException;
import com.lsoe.coolreader.test.enums.Country;
import com.lsoe.coolreader.test.models.User;

/**
 * TODO: Describe purpose and behavior of CoolReaderAnnotationNegativeTest
 */
public class CoolReaderAnnotationNegativeTest {

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv")
	@CoolConstructor(constructorClass = User.class, paramTypes = {String.class,
			double.class, boolean.class, int.class, Country.class})
	Object[][] data;
	@Test(expected = CoolReaderException.class)
	public void testUnabletToInjectDataToNonPublicField() throws Exception {
		CoolReader.inject(this);
	}

}
