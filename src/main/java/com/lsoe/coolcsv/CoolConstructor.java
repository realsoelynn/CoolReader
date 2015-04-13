package com.lsoe.coolcsv;

/**
 * TODO: Describe purpose and behavior of CoolConstructor
 */
public class CoolConstructor {

	private Class<?> constructorClass;
	private CoolConstructorParams constructorParams;

	// TODO: Disable the default constructor and write print function to show
	// the tutorial on how to write correctly

	public CoolConstructor(Class<?> constructorClass,
			CoolConstructorParams constructorParams) {

		this.constructorClass = constructorClass;
		this.constructorParams = constructorParams;
	}

	public Class<?> getConstructorClass() {
		return constructorClass;
	}

	public void setConstructorClass(Class<?> constructorClass) {
		this.constructorClass = constructorClass;
	}

	public CoolConstructorParams getConstructorParams() {
		return constructorParams;
	}

	public void setConstructorParams(CoolConstructorParams constructorParams) {
		this.constructorParams = constructorParams;
	}

}
