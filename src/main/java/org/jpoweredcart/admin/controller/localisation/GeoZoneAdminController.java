package org.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.GeoZone;
import org.jpoweredcart.admin.entity.localisation.Zone;
import org.jpoweredcart.admin.entity.localisation.ZoneToGeoZone;
import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import org.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class GeoZoneAdminController extends BaseController {
	
	@Inject
	private ZoneAdminModel zoneAdminModel;
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private GeoZoneAdminModel geoZoneAdminModel;
	
	@RequestMapping(value="/admin/localisation/geoZone")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<GeoZone> currencies = geoZoneAdminModel.getGeoZones(pageParam);
		model.addAttribute("geoZones", currencies);
		int total = geoZoneAdminModel.getTotalGeoZones();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/geoZone"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/geoZoneList";
	}
	
	@RequestMapping(value="/admin/localisation/geoZone/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		model.addAttribute("geoZone", new GeoZone());
		model.addAttribute("countries", countryAdminModel.getAllCountries());
		
		return "/admin/localisation/geoZoneForm";
	}
	
	@RequestMapping(value="/admin/localisation/geoZone/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		GeoZone geoZone = geoZoneAdminModel.getGeoZone(id);
		model.addAttribute("geoZone", geoZone);
		model.addAttribute("countries", countryAdminModel.getAllCountries());
		
		return "/admin/localisation/geoZoneForm";
	}
	
	@RequestMapping(value="/admin/localisation/geoZone/save", method=RequestMethod.POST)
	public String save(@Valid GeoZone geoZone, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("geoZone", geoZone);
			return "/admin/localisation/geoZoneForm";
		}
		
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
				geoZone.getZoneToGeoZones().add(ztgz);
			}
		}
		
		geoZoneAdminModel.saveGeoZone(geoZone);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/geoZone";
	}
	
	@RequestMapping(value="/admin/localisation/geoZone/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				geoZoneAdminModel.deleteGeoZone(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/geoZone";
	}
	
	@RequestMapping(value="/admin/localisation/geoZone/getZoneOptions.html", method=RequestMethod.GET)
	public @ResponseBody String getZoneOptions(@RequestParam("countryId") Integer countryId, 
			@RequestParam("zoneId") Integer zoneId, HttpServletRequest request){
		
		logger.debug("getZoneOptions.html?countryId={}&zoneId={}", countryId, zoneId );
		StringBuilder output = new StringBuilder();
		output.append("<option value=\"0\">");
		output.append(message(request, "text.allZones"));
		output.append("</option>");
		
		List<Zone> zoneList = zoneAdminModel.getZonesByCountryId(countryId);
		for(Zone zone : zoneList){
			output.append("<option value=\"").append(zone.getId()).append("\"");
			if(zone.getId().equals(zoneId)){
				output.append("selected=\"selected\"");
			}
			output.append(">").append(zone.getName()).append("</option>");
		}
		logger.debug("getZoneOptions return size:{}", zoneList.size());
		return output.toString();
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/geo_zone", 
				new UnauthorizedAdminException());
	}
	
}
