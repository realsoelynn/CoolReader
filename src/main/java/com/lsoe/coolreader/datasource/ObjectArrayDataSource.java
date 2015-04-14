package com.lsoe.coolreader.datasource;

import java.util.ArrayList;

import com.lsoe.coolreader.CoolColumn;
import com.lsoe.coolreader.CoolDataSource;
import com.lsoe.coolreader.CoolRecord;

/**
 * TODO: Describe purpose and behavior of ObjectArrayDataSource
 */
public class ObjectArrayDataSource extends CoolDataSource {

	private Object[][] data;

	public ObjectArrayDataSource(Object[][] data) {
		this(null, data);
	}

	public ObjectArrayDataSource(CoolColumn[] columns, Object[][] data) {
		super(columns);
		this.data = data;
	}

	public CoolRecord[] read() throws Exception {

		ArrayList<CoolRecord> coolRecords = new ArrayList<CoolRecord>();

		for (Object[] record : data) {

			if (columns == null) {

				ArrayList<CoolColumn> coolColumns = new ArrayList<CoolColumn>();

				// Build CoolColumns so that Object Type will be intact.
				for (int i = 0; i < record.length; i++) {
					CoolColumn coolColumn = new CoolColumn(null, i,
							record[i].getClass());
					coolColumns.add(coolColumn);
				}

				columns = coolColumns
						.toArray(new CoolColumn[coolColumns.size()]);
			}

			CoolRecord coolRecord = new CoolRecord(columns, record);
			coolRecords.add(coolRecord);
		}

		return coolRecords.toArray(new CoolRecord[coolRecords.size()]);
	}
}
