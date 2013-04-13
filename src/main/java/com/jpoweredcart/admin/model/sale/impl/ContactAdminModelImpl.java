package com.jpoweredcart.admin.model.sale.impl;

import javax.inject.Inject;

import com.jpoweredcart.admin.form.sale.ContactForm;
import com.jpoweredcart.admin.model.sale.AffiliateAdminModel;
import com.jpoweredcart.admin.model.sale.ContactAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.sale.OrderAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.system.email.EmailService;

public class ContactAdminModelImpl extends BaseModel implements ContactAdminModel {

	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@Inject
	private CustomerAdminModel customerAdminModel;
	
	@Inject
	private AffiliateAdminModel affiliateAdminModel;
	
	@Inject
	private OrderAdminModel orderAdminModel;
	
	@Inject
	private EmailService emailService;
	
	@Override
	public void sendEmails(ContactForm contactForm) {
		
	}
	
	
}
