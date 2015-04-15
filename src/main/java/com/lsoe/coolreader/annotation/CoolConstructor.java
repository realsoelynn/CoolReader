package com.lsoe.coolreader.annotation;

/**
 * TODO: Describe purpose and behavior of CoolConstructor
 */
public @interface CoolConstructor {

	Class<?> constructorClass();

	Class<?> paramTypes();
}