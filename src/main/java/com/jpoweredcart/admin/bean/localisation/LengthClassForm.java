package com.jpoweredcart.admin.bean.localisation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;

public class LengthClassForm extends LengthClass{
	
	private static final long serialVersionUID = 1L;

	@Valid
	protected List<LengthClassDesc> descs = new AutoPopulatingList<LengthClassDesc>(LengthClassDesc.class);
	

	public List<LengthClassDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<LengthClassDesc> descs) {
		this.descs = descs = new AutoPopulatingList<LengthClassDesc>(descs, LengthClassDesc.class);
	}
	
}
