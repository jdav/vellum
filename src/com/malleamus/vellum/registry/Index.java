package com.malleamus.vellum.registry;

import java.lang.reflect.Field;
import java.util.Hashtable;

import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumbers;


public class Index extends Hashtable<String, RegistrationNumbers> {

	private static final long serialVersionUID = 4954463097260104625L;
	
	public static String getIndexName(Persisting entry) {
		return getIndexName(entry.getObjectType(), entry.getFieldName());
	}
	
	public static String getIndexName(String objectType, String fieldName) {
		return objectType + "-" + fieldName;
	}
	
	private Field field = null;
	
	public Index(Field field) {
		super();
		this.field = field;
	}
	
	public String getName() {
		return this.field.getName();
	}
	
	/**
	 * The input key should be a string representing the value
	 * of a field in a propsistable object.
	 */
	public RegistrationNumbers get(Object key) {
		RegistrationNumbers resultSet = super.get(key);
		if (resultSet == null) {
			resultSet = new RegistrationNumbers();
			super.put((String) key, resultSet);
		}
		return resultSet;
	}
	
	public RegistrationNumbers remove(Object propsistable) {
		RegistrationNumbers list = null;
		try {
			list = get(field.get(propsistable).toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (list.contains(propsistable)) {
			list.remove(propsistable);
		}
		return list;
	}

	public void put(Registerable propsistable) {
		RegistrationNumbers list = null;
		try {
			list = get(field.get(propsistable).toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!list.contains(propsistable.getRegistrationNumber())) {
			list.add(propsistable.getRegistrationNumber());
		}
	}
}
