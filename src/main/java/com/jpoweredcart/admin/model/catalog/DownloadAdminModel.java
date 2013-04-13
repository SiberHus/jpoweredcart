package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.DownloadForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.entity.catalog.DownloadDesc;

public interface DownloadAdminModel {
	
	public void create(DownloadForm dlForm);
	
	public void update(DownloadForm dlForm);
	
	public void delete(Integer dlId);
	
	public DownloadForm newForm();
	
	public DownloadForm getForm(Integer dlId);
	
	public Download get(Integer dlId);
	
	public List<Download> getList(PageParam pageParam);
	
	public List<DownloadDesc> getDescriptions(Integer dlId);
	
	public int getTotal();
	
}
