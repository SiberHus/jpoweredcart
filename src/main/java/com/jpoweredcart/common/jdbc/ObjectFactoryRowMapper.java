package com.jpoweredcart.common.jdbc;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public abstract class ObjectFactoryRowMapper<T> implements RowMapper<T>{
	
	private Class<? extends T> targetClass;
	
	@SuppressWarnings("unchecked")
	public ObjectFactoryRowMapper(){
		Object genericSuperClass = this.getClass().getGenericSuperclass();
		if(genericSuperClass instanceof ParameterizedType){
			ParameterizedType ptype = (ParameterizedType)genericSuperClass;
			this.targetClass = (Class<? extends T>)ptype.getActualTypeArguments()[0];
		}
	}
	
	public ObjectFactoryRowMapper(Class<? extends T> clazz){
		this.targetClass = clazz;
	}
	
	public Class<? extends T> getTargetClass() {
		return targetClass;
	}
	
	public ObjectFactoryRowMapper<T> setTargetClass(Class<? extends T> targetClass) {
		this.targetClass = targetClass;
		return this;
	}
	
	public T newInstance() {
		if(this.targetClass==null){
			throw new IllegalStateException("targetClass hasn't been set");
		}
		try{
			return this.targetClass.newInstance();
		}catch(Exception e){
			throw new RuntimeException("Cannot instantiate class: "+targetClass);
		}
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T object = newInstance();
		return mapRow(rs, object);
	}
	
	public abstract T mapRow(ResultSet rs, T object) throws SQLException;
	
}
