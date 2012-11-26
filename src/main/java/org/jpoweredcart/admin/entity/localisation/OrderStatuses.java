package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.AutoPopulatingList;

public class OrderStatuses implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean newEntities = true;
	
	private List<OrderStatus> list;
	
	public OrderStatuses(){
		this.list = new AutoPopulatingList<OrderStatus>(OrderStatus.class);
	}
	
	public OrderStatuses(List<OrderStatus> descs){
		this.list = new AutoPopulatingList<OrderStatus>(descs, OrderStatus.class);
	}
	
	public List<OrderStatus> getList() {
		return list;
	}
	
	public void setList(List<OrderStatus> list) {
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
		for(OrderStatus orderStatus: list){
			int nameLength = StringUtils.length(orderStatus.getName());
			if(nameLength<3 || nameLength>32){
				orderStatus.setError(true);
				hasErrors = true;
			}
		}
		return hasErrors;
	}
	
	
	public static class OrderStatus implements Serializable {
		
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

