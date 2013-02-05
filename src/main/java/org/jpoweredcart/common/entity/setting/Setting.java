package org.jpoweredcart.common.entity.setting;

import java.io.Serializable;

public class Setting  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer storeId;
	
	protected String group;
	
	protected String key;
	
	protected String value;
	
	protected boolean serialized;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSerialized() {
		return serialized;
	}

	public void setSerialized(boolean serialized) {
		this.serialized = serialized;
	}
	
}
