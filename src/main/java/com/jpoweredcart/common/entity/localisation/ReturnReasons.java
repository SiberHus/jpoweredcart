package org.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.AutoPopulatingList;

public class ReturnReasons implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean newEntities = true;
	
	private List<ReturnReason> list;
	
	public ReturnReasons(){
		this.list = new AutoPopulatingList<ReturnReason>(ReturnReason.class);
	}
	
	public ReturnReasons(List<ReturnReason> descs){
		this.list = new AutoPopulatingList<ReturnReason>(descs, ReturnReason.class);
	}
	
	public List<ReturnReason> getList() {
		return list;
	}
	
	public void setList(List<ReturnReason> list) {
		this.list = list;
	}
	
	public boolean isNewEntities() {
		return newEntities;
	}
	
	public void setNewEntities(boolean newEntities) {
		this.newEntities = newEntities;
	}
	
	public boolean hasErrors(){
		boolean hasErrors = false;
		for(ReturnReason returnReason: list){
			int nameLength = StringUtils.length(returnReason.getName());
			if(nameLength<3 || nameLength>32){
				returnReason.setError(true);
				hasErrors = true;
			}
		}
		return hasErrors;
	}
	
	
	public static class ReturnReason implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		protected Integer id;
		
		protected Integer languageId;
		
		private String languageName;

		private String languageImage;
		
		@NotBlank
		protected String name;
		
		private boolean error = false;
		
		public String getLanguageName() {
			return languageName;
		}

		public void setLanguageName(String languageName) {
			this.languageName = languageName;
		}

		public String getLanguageImage() {
			return languageImage;
		}

		public void setLanguageImage(String languageImage) {
			this.languageImage = languageImage;
		}

		public boolean isError() {
			return error;
		}

		public void setError(boolean error) {
			this.error = error;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getLanguageId() {
			return languageId;
		}

		public void setLanguageId(Integer languageId) {
			this.languageId = languageId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}

