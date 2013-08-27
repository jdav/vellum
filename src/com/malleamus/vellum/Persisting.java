package com.malleamus.vellum;

public class Persisting {

	private String objectType = null;
	private RegistrationNumber rn = RegistrationNumber.UNREGISTERED;
	private String fieldName = null;
	private String value = null;
	
	public Persisting() {}

	public Persisting(RegistrationNumber rn,
					  String objectType, 
			          String fieldName, 
			          String value) {
		this.rn = rn;
		this.objectType = objectType;
		this.fieldName = fieldName;
		this.value = value;
	}
	
	public Persisting(String nameValue) {
		setObjectType(nameValue.split("-")[0].trim());
		setRegistrationNumber(new RegistrationNumber(nameValue.split("-")[1].trim()));
		setFieldName(nameValue.split("-")[2].trim());
		setValue(nameValue.split("-")[3].trim());
	}
	
	public Persisting(String name, String value) {
		setObjectType(name.split("-")[0].trim());
		setRegistrationNumber(new RegistrationNumber(name.split("-")[1].trim()));
		setFieldName(name.split("-")[2].trim());
		setValue(value);
	}
	
	public Persisting(RegistrationNumber parentRN, 
			          String objectType,
			          String name, 
            		  Registerable value) {
		setRegistrationNumber(parentRN);
		setObjectType(objectType);
		setFieldName(name);
		setValue(value.getRegistrationNumber().toString());
	}
	
	public Persisting(RegistrationNumber parentRN, 
			  		  String objectType, 
			  		  String name, 
			  		  Registerables value) {
		setObjectType(objectType);
		setRegistrationNumber(parentRN);
		setFieldName(name);
		setValue(value.toString());
	}

	public String getObjectType() {
		return objectType;
	}
	
	public RegistrationNumber getRegistrationNumber() {
		return rn;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getName() {
		return objectType + '-' + rn + '-' + fieldName;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getIndexName() {
		return objectType + '-' + fieldName;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * @param rn the rn to set
	 */
	public void setRegistrationNumber(RegistrationNumber rn) {
		this.rn = rn;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return getName() + '=' + getValue();
	}
	
	public boolean equals(Object o) {
		return o != null && 
			   o instanceof Persisting &&
			   ((Persisting) o).getName().equals(this.getName());
	}
	
	public int hashCode() {
		return getName().hashCode();
	}
}
