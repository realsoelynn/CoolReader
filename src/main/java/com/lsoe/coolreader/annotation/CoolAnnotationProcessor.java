package com.lsoe.coolreader.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.lsoe.coolreader.CoolColumn;
import com.lsoe.coolreader.CoolConstructorParams;
import com.lsoe.coolreader.CoolReader;
import com.lsoe.coolreader.exception.CoolReaderException;

/**
 * TODO: Describe purpose and behavior of CoolAnnotationProcessor
 */
public class CoolAnnotationProcessor {

	// TODO: Add a processor so that public Field declaration can be detected at
	// compile time. Currently handled in runtime

	public static void process(Object objectToProcess) throws Exception {

		for (Field field : objectToProcess.getClass().getDeclaredFields()) {

			if (field.isAnnotationPresent(CoolData.class) == false) {
				continue;
			}

			CoolData coolData = field.getAnnotation(CoolData.class);
			CoolConstructor coolConstructor = field
					.getAnnotation(CoolConstructor.class);
			CoolConstructors coolConstructors = field
					.getAnnotation(CoolConstructors.class);

			try {
				if (coolData != null) {
					CoolReader reader = new CoolReader(coolData.csvFileURI());

					if (coolConstructors != null) {

						ArrayList<com.lsoe.coolreader.CoolConstructor> constructors = new ArrayList<com.lsoe.coolreader.CoolConstructor>();

						for (CoolConstructor coolConstructor2 : coolConstructors
								.value()) {
							com.lsoe.coolreader.CoolConstructor constructor = new com.lsoe.coolreader.CoolConstructor(
									coolConstructor2.constructorClass(),
									new CoolConstructorParams(
											coolConstructor2.paramTypes()));

							constructors.add(constructor);
						}

						field.set(
								objectToProcess,
								reader.readAllAsCustomObject(constructors
										.toArray(new com.lsoe.coolreader.CoolConstructor[constructors
												.size()])));
					} else if (coolConstructor != null) {
						field.set(objectToProcess, reader
								.readAllAsCustomObject(coolConstructor
										.constructorClass(),
										new CoolConstructorParams(
												coolConstructor.paramTypes())));
					} else if (coolData.columns() != null) {
						CoolColumn[] columns = new CoolColumn[coolData
								.columns().length];

						for (int i = 0; i < columns.length; i++) {
							columns[i] = new CoolColumn("", i,
									coolData.columns()[i]);
						}

						reader.getDatasource().setColumns(columns);

						field.set(objectToProcess, reader.readAll());
					}
				}
			} catch (IllegalAccessException illegalAccessException) {
				throw new CoolReaderException(
						String.format(
								"\n%1$s {\n       %2$s %3$s; => public %2$s %3$s;\n}\n Please do the change suggested so that CoolReader will be able to inject the data.",
								field.getDeclaringClass(), field
										.getGenericType().getTypeName(), field
										.getName()));
			}
		}
	}
}
