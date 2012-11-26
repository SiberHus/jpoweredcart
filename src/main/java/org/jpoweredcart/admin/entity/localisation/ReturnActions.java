package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.AutoPopulatingList;

public class ReturnActions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean newEntities = true;
	
	private List<ReturnAction> list;
	
	public ReturnActions(){
		this.list = new AutoPopulatingList<ReturnAction>(ReturnAction.class);
	}
	
	public ReturnActions(List<ReturnAction> descs){
		this.list = new AutoPopulatingList<ReturnAction>(descs, ReturnAction.class);
	}
	
	public List<ReturnAction> getList() {
		return list;
	}
	
	public void setList(List<ReturnAction> list) {
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
		for(ReturnAction returnStatus: list){
			int nameLength = StringUtils.length(returnStatus.getName());
			if(nameLength<3 || nameLength>32){
				returnStatus.setError(true);
				hasErrors = true;
			}
		}
		return hasErrors;
	}
	
	
	public static class ReturnAction implements Serializable {
		
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

