package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductOptionValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer productOptionId;
	
	protected Integer productId;
	
	protected Integer optionId;
	
	protected Integer optionValueId;
	
	protected int quantity;
	
	protected boolean subtract;
	
	protected BigDecimal price;
	
	protected String pricePrefix;
	
	protected int points;
	
	protected String pointsPrefix;
	
	protected double weight;
	
	protected String weightPrefix;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(Integer productOptionId) {
		this.productOptionId = productOptionId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getOptionValueId() {
		return optionValueId;
	}

	public void setOptionValueId(Integer optionValueId) {
		this.optionValueId = optionValueId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isSubtract() {
		return subtract;
	}

	public void setSubtract(boolean subtract) {
		this.subtract = subtract;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPricePrefix() {
		return pricePrefix;
	}

	public void setPricePrefix(String pricePrefix) {
		this.pricePrefix = pricePrefix;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getPointsPrefix() {
		return pointsPrefix;
	}

	public void setPointsPrefix(String pointsPrefix) {
		this.pointsPrefix = pointsPrefix;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getWeightPrefix() {
		return weightPrefix;
	}

	public void setWeightPrefix(String weightPrefix) {
		this.weightPrefix = weightPrefix;
	}
	
}
