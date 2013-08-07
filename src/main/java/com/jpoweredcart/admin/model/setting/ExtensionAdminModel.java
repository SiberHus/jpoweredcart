package com.jpoweredcart.admin.model.setting;

import java.util.List;

import com.jpoweredcart.common.entity.setting.Extension;

public interface ExtensionAdminModel {
	
	public List<Extension> getInstalled(String type);
	
	public void install(String type, String code);
	
	public void uninstall(String type, String code);
	
	
}
