package org.jpoweredcart.common.entity.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer invoiceNo;
	
	protected String invoicePrefix;
	
	protected Integer storeId;
	
	protected String storeName;
	
	protected String storeUrl;
	
	protected Integer customerId;
	
	protected Integer customerGroupId;
	
	protected String firstName;
	
	protected String lastName;
	
	protected String email;
	
	protected String telephone;
	
	protected String fax;
	
	protected String paymentFirstName;
	
	protected String paymentLastName;
	
	protected String paymentCompany;

	protected String paymentCompanyId;
	
	protected String paymentTaxId;
	
	protected String paymentAddress1;
	
	protected String paymentAddress2;
	
	protected String paymentPostcode;
	
	protected String paymentCity;
	
	protected Integer paymentZoneId;
	
	protected String paymentZone;
	
	protected String paymentZoneCode;
	
	protected Integer paymentCountryId;
	
	protected String paymentCountry;
	
	protected String paymentIsoCode2;
	
	protected String paymentIsoCode3;
	
	protected String paymentAddressFormat;
	
	protected String paymentMethod;
	
	protected String paymentCode;
	
	protected String shippingFirstName;
	
	protected String shippingLastName;
	
	protected String shippingCompany;
	
	protected String shippingAddress1;
	
	protected String shippingAddress2;
	
	protected String shippingPostcode;
	
	protected String shippingCity;
	
	protected Integer shippingZoneId;
	
	protected String shippingZone;
	
	protected String shippingZoneCode;
	
	protected Integer shippingCountryId;
	
	protected String shippingCountry;
	
	protected String shippingIsoCode2;
	
	protected String shippingIsoCode3;
	
	protected String shippingAddressFormat;
	
	protected String shippingMethod;
	
	protected String shippingCode;
	
	protected String comment;
	
	protected BigDecimal total;
	
	protected Integer orderStatusId;
	
	protected Integer affiliateId;
	
	protected BigDecimal commission;
	
	protected Integer languageId;
	
	protected String languageCode;
	
	protected String languageFileName;
	
	protected String languageDirectory;
	
	protected Integer currencyId;
	
	protected String currencyCode;
	
	protected BigDecimal currencyValue;
	
	protected String ip;
	
	protected String forwardIp;
	
	protected String userAgent;
	
	protected String acceptLanguage;
	
	protected Date dateAdded;
	
	protected Date dateModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Integer invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoicePrefix() {
		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPaymentFirstName() {
		return paymentFirstName;
	}

	public void setPaymentFirstName(String paymentFirstName) {
		this.paymentFirstName = paymentFirstName;
	}

	public String getPaymentLastName() {
		return paymentLastName;
	}

	public void setPaymentLastName(String paymentLastName) {
		this.paymentLastName = paymentLastName;
	}

	public String getPaymentCompany() {
		return paymentCompany;
	}

	public void setPaymentCompany(String paymentCompany) {
		this.paymentCompany = paymentCompany;
	}

	public String getPaymentCompanyId() {
		return paymentCompanyId;
	}

	public void setPaymentCompanyId(String paymentCompanyId) {
		this.paymentCompanyId = paymentCompanyId;
	}

	public String getPaymentTaxId() {
		return paymentTaxId;
	}

	public void setPaymentTaxId(String paymentTaxId) {
		this.paymentTaxId = paymentTaxId;
	}

	public String getPaymentAddress1() {
		return paymentAddress1;
	}

	public void setPaymentAddress1(String paymentAddress1) {
		this.paymentAddress1 = paymentAddress1;
	}

	public String getPaymentAddress2() {
		return paymentAddress2;
	}

	public void setPaymentAddress2(String paymentAddress2) {
		this.paymentAddress2 = paymentAddress2;
	}

	public String getPaymentPostcode() {
		return paymentPostcode;
	}

	public void setPaymentPostcode(String paymentPostcode) {
		this.paymentPostcode = paymentPostcode;
	}

	public String getPaymentCity() {
		return paymentCity;
	}

	public void setPaymentCity(String paymentCity) {
		this.paymentCity = paymentCity;
	}

	public Integer getPaymentZoneId() {
		return paymentZoneId;
	}

	public void setPaymentZoneId(Integer paymentZoneId) {
		this.paymentZoneId = paymentZoneId;
	}

	public String getPaymentZone() {
		return paymentZone;
	}

	public void setPaymentZone(String paymentZone) {
		this.paymentZone = paymentZone;
	}

	public String getPaymentZoneCode() {
		return paymentZoneCode;
	}

	public void setPaymentZoneCode(String paymentZoneCode) {
		this.paymentZoneCode = paymentZoneCode;
	}

	public Integer getPaymentCountryId() {
		return paymentCountryId;
	}

	public void setPaymentCountryId(Integer paymentCountryId) {
		this.paymentCountryId = paymentCountryId;
	}

	public String getPaymentCountry() {
		return paymentCountry;
	}

	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	public String getPaymentIsoCode2() {
		return paymentIsoCode2;
	}

	public void setPaymentIsoCode2(String paymentIsoCode2) {
		this.paymentIsoCode2 = paymentIsoCode2;
	}

	public String getPaymentIsoCode3() {
		return paymentIsoCode3;
	}

	public void setPaymentIsoCode3(String paymentIsoCode3) {
		this.paymentIsoCode3 = paymentIsoCode3;
	}

	public String getPaymentAddressFormat() {
		return paymentAddressFormat;
	}

	public void setPaymentAddressFormat(String paymentAddressFormat) {
		this.paymentAddressFormat = paymentAddressFormat;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getShippingFirstName() {
		return shippingFirstName;
	}

	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	public String getShippingLastName() {
		return shippingLastName;
	}

	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public String getShippingPostcode() {
		return shippingPostcode;
	}

	public void setShippingPostcode(String shippingPostcode) {
		this.shippingPostcode = shippingPostcode;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public Integer getShippingZoneId() {
		return shippingZoneId;
	}

	public void setShippingZoneId(Integer shippingZoneId) {
		this.shippingZoneId = shippingZoneId;
	}

	public String getShippingZone() {
		return shippingZone;
	}

	public void setShippingZone(String shippingZone) {
		this.shippingZone = shippingZone;
	}

	public String getShippingZoneCode() {
		return shippingZoneCode;
	}

	public void setShippingZoneCode(String shippingZoneCode) {
		this.shippingZoneCode = shippingZoneCode;
	}

	public Integer getShippingCountryId() {
		return shippingCountryId;
	}

	public void setShippingCountryId(Integer shippingCountryId) {
		this.shippingCountryId = shippingCountryId;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingIsoCode2() {
		return shippingIsoCode2;
	}

	public void setShippingIsoCode2(String shippingIsoCode2) {
		this.shippingIsoCode2 = shippingIsoCode2;
	}

	public String getShippingIsoCode3() {
		return shippingIsoCode3;
	}

	public void setShippingIsoCode3(String shippingIsoCode3) {
		this.shippingIsoCode3 = shippingIsoCode3;
	}

	public String getShippingAddressFormat() {
		return shippingAddressFormat;
	}

	public void setShippingAddressFormat(String shippingAddressFormat) {
		this.shippingAddressFormat = shippingAddressFormat;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Integer getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(Integer affiliateId) {
		this.affiliateId = affiliateId;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageFileName() {
		return languageFileName;
	}

	public void setLanguageFileName(String languageFileName) {
		this.languageFileName = languageFileName;
	}

	public String getLanguageDirectory() {
		return languageDirectory;
	}

	public void setLanguageDirectory(String languageDirectory) {
		this.languageDirectory = languageDirectory;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(BigDecimal currencyValue) {
		this.currencyValue = currencyValue;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getForwardIp() {
		return forwardIp;
	}

	public void setForwardIp(String forwardIp) {
		this.forwardIp = forwardIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
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
	
}
