package com.lsoe.coolcsv;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.lsoe.coolcsv.internal.CoolCSVColumn;
import com.lsoe.coolcsv.test.enums.Country;

public class CoolCSVRecordTest {

    @Test
    public void run() throws Exception {
        CoolCSVColumn countryColumn = new CoolCSVColumn("userCountry", 0, "com.lsoe.coolcsv.test.enums.Country");
        CoolCSVColumn userNameColumn = new CoolCSVColumn("userName", 1, "String");
        CoolCSVColumn[] columns = new CoolCSVColumn[] { countryColumn, userNameColumn, };
        String[] record = new String[] { "MM", "Soe Lynn" };

        CoolCSVRecord coolRecord = new CoolCSVRecord(columns, record);
        Country country = (Country) coolRecord.get("userCountry");
        assertEquals("Myanmar", country.getCountry());
    }
}
