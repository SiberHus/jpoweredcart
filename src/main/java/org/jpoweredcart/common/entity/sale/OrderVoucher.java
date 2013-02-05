package org.jpoweredcart.common.entity.sale;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderVoucher implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer orderId;
	
	protected Integer voucherId;
	
	protected String description;
	
	protected String code;
	
	protected String fromName;
	
	protected String fromEmail;
	
	protected String toName;
	
	protected String toEmail;
	
	protected Integer voucherThemeId;
	
	protected String message;
	
	protected BigDecimal amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Integer voucherId) {
		this.voucherId = voucherId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public Integer getVoucherThemeId() {
		return voucherThemeId;
	}

	public void setVoucherThemeId(Integer voucherThemeId) {
		this.voucherThemeId = voucherThemeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
