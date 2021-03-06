package com.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.form.localisation.GeoZoneForm;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import com.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.GeoZone;
import com.jpoweredcart.common.entity.localisation.Zone;
import com.jpoweredcart.common.entity.localisation.ZoneToGeoZone;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping(value="/admin/localisation/geoZone")
public class GeoZoneAdminController extends BaseController {
	
	@Inject
	private ZoneAdminModel zoneAdminModel;
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private GeoZoneAdminModel geoZoneAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<GeoZone> geoZoneList = geoZoneAdminModel.getList(pageParam);
		model.addAttribute("geoZones", geoZoneList);
		int total = geoZoneAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/geoZone"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/geoZoneList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		model.addAttribute("geoZoneForm", geoZoneAdminModel.newForm());
		model.addAttribute("countries", countryAdminModel.getAll());
		
		return "/admin/localisation/geoZoneForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		GeoZoneForm geoZoneForm = geoZoneAdminModel.getForm(id);
		model.addAttribute("geoZoneForm", geoZoneForm);
		model.addAttribute("countries", countryAdminModel.getAll());
		
		return "/admin/localisation/geoZoneForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid GeoZoneForm geoZoneForm, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		String countryIds[] = request.getParameterValues("zoneToGeoZones.countryId");
		String zoneIds[] = request.getParameterValues("zoneToGeoZones.zoneId");
		if(countryIds!=null){
			//i=1 because the first one is dummy value from prototype
			for(int i=1;i<countryIds.length;i++){
				int countryId = getConversionService().convert(countryIds[i], Integer.class);
				int zoneId = getConversionService().convert(zoneIds[i], Integer.class);
				ZoneToGeoZone ztgz = new ZoneToGeoZone();
				ztgz.setCountryId(countryId);
				ztgz.setZoneId(zoneId);
				geoZoneForm.getZoneToGeoZones().add(ztgz);
			}
		}
		
		if(result.hasErrors()){
			model.addAttribute("geoZoneForm", geoZoneForm);
			model.addAttribute("countries", countryAdminModel.getAll());
			return "/admin/localisation/geoZoneForm";
		}
		
		if(geoZoneForm.getId()!=null){
			geoZoneAdminModel.update(geoZoneForm);
		}else{
			geoZoneAdminModel.create(geoZoneForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/geoZone";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected", required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				geoZoneAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/localisation/geoZone";
	}
	
	@RequestMapping(value="/zoneOptions.html", method=RequestMethod.GET)
	public @ResponseBody String getZoneOptions(@RequestParam("countryId") Integer countryId, 
			@RequestParam("zoneId") Integer zoneId, HttpServletRequest request){
		
		logger.debug("zoneOptions.html?countryId={}&zoneId={}", countryId, zoneId );
		StringBuilder output = new StringBuilder();
		output.append("<option value=\"0\">");
		output.append(message(request, "text.allZones"));
		output.append("</option>");
		
		List<Zone> zoneList = zoneAdminModel.getAllByCountryId(countryId);
		for(Zone zone : zoneList){
			output.append("<option value=\"").append(zone.getId()).append("\"");
			if(zone.getId().equals(zoneId)){
				output.append("selected=\"selected\"");
			}
			output.append(">").append(zone.getName()).append("</option>");
		}
		logger.debug("zoneOptions return size:{}", zoneList.size());
		return output.toString();
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/geo_zone", 
				new UnauthorizedAdminException());
	}
	
}
