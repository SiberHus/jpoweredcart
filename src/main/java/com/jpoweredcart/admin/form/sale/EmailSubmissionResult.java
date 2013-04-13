package com.jpoweredcart.admin.form.sale;

import java.util.HashMap;
import java.util.Map;

public class EmailSubmissionResult {
	
	private Map<String, String> errors = new HashMap<String, String>();
	
	private String success = "";
	
	public void addError(String field, String message){
		errors.put(field, message);
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
}
