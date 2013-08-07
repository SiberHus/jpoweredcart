package com.jpoweredcart.common.utils;

public final class Reflections {
	
	
	public static <T> T newInstance(Class<T> clazz, String className) {

		try {
			Class<?> targetClass = Class.forName(className);
			@SuppressWarnings("unchecked")
			T model = (T) targetClass.newInstance();
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
