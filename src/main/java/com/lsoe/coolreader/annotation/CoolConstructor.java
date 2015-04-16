package com.lsoe.coolreader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: Describe purpose and behavior of CoolConstructor
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoolConstructor {

	Class<?> constructorClass();

	Class<?>[] paramTypes();
}