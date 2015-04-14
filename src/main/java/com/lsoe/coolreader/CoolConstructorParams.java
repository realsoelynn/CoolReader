package com.lsoe.coolreader;

/**
 * TODO: Describe purpose and behavior of CoolConstructorParams
 */
public class CoolConstructorParams {

	private Class<?>[] paramsType;

	public CoolConstructorParams(Class<?>... paramsType) {
		this.paramsType = paramsType;
	}

	public Class<?>[] getParamsType() {
		return paramsType;
	}

	public void setParamsType(Class<?>... paramsType) {
		this.paramsType = paramsType;
	}

}
