package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String image;
	
	protected String path;
	
	protected Integer parentId;
	
	protected boolean top;
	
	protected int column;
	
	protected int sortOrder;
	
	protected int status;
	
	protected Date dateAdded;
	
	protected Date dateModified;

	protected List<CategoryDesc> descs;
	
	public Category(){
		this.descs = new AutoPopulatingList<CategoryDesc>(CategoryDesc.class);
	}
	
	public Category(List<CategoryDesc> descs){
		this.descs = new AutoPopulatingList<CategoryDesc>(descs, CategoryDesc.class);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public List<CategoryDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<CategoryDesc> descs) {
		this.descs = descs;
	}
	
}
