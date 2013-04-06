package com.jpoweredcart.admin.controller.common;

import java.io.FileFilter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.file.ActionResult;
import com.jpoweredcart.common.service.file.DirectoryInfo;
import com.jpoweredcart.common.service.file.FileFilters;
import com.jpoweredcart.common.service.file.FileInfo;
import com.jpoweredcart.common.service.file.FileService;
import com.jpoweredcart.common.service.media.MediaService;
import com.jpoweredcart.common.utils.PathUtils;

@Controller
@RequestMapping("/admin/common/fileManager")
public class FileManagerAdminController extends BaseController {
	
	@Inject
	private MediaService mediaService;
	
	@Inject
	private FileService mediaFileService;
	
	@RequestMapping(value={"","/"})
	public String index(Model model, HttpServletRequest request){
		
		String field = request.getParameter("field");
		model.addAttribute("field", field!=null?field:"");
		String fckeditor = request.getParameter("CKEditorFuncNum");
		model.addAttribute("fckeditor", fckeditor!=null?"yes":"");
		
		return "/admin/common/fileManager";
	}
	
	@RequestMapping(value="/image", produces={"text/plain"})
	public @ResponseBody String getImage(@RequestParam String image, HttpServletRequest request){
		
		String thumbnailUrl = mediaService.getThumbnailUrl(image, 100, 100);
		logger.debug("Request image: {}", image);
		logger.debug("Thumbnail URL: {}", thumbnailUrl);
		
		return thumbnailUrl;
	}
	
	@RequestMapping(value="/files")
	public @ResponseBody List<FileInfo> getFiles(@RequestParam(required=false) String directory){
		
		FileFilter imageFileFilter = new FileFilters.MediaFileFilter();
		
		return mediaFileService.getFiles(directory, imageFileFilter);
	}
	
	@RequestMapping(value="/directory")
	public @ResponseBody List<DirectoryInfo> getDirectories(@RequestParam(value="directory", required=false) String directory){
		
		return mediaFileService.getDirectories(directory);
	}
	
	
	@RequestMapping(value="/create")
	public @ResponseBody ActionResult createDirectory(@RequestParam(value="directory", required=false) String directory, 
			@RequestParam(value="name", required=false) String name, HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		directory = PathUtils.ensureEndingFileSeparator(directory);
		
		if(StringUtils.isNotEmpty(directory)){
			if(!mediaFileService.isDirectory(directory)){
				result.setError(message(request, "error.directory"));
			}
			
			if(StringUtils.isNotEmpty(name)){
				if(mediaFileService.exists(directory+name)){
					result.setError(message(request, "error.exists"));
				}
			}else{
				result.setError(message(request, "error.name"));
			}
		}else{
			result.setError(message(request, "error.directory"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		
		if(result.getError()==null){
			if(mediaFileService.makeDir(directory, name)){
				result.setSuccess(message(request, "text.create"));
			}else{
				result.setError(message(request, "error.directory"));
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/delete")
	public @ResponseBody ActionResult delete(@RequestParam(value="path", required=false) String path,
			HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		if(StringUtils.isNotEmpty(path)){
			if(!mediaFileService.exists(path)){
				result.setError(message(request, "error.select"));
			}
			
			if(path=="/"){
				result.setError(message(request, "error.delete"));
			}
		}else{
			result.setError(message(request, "error.select"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		if(result.getError()==null){
			if(mediaFileService.delete(path)){
				result.setSuccess(message(request, "text.delete"));
			}else{
				result.setError(message(request, "error.delete"));
			}
		}
		return result;
	}
	
	@RequestMapping(value="/move")
	public @ResponseBody ActionResult delete(@RequestParam(value="from", required=false) String from,
			@RequestParam(value="to", required=false) String to, HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		if(StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)){
			if(!mediaFileService.exists(from)){
				result.setError(message(request, "error.missing"));
			}else if(from=="/"){
				result.setError(message(request, "error.default"));
			}
			if(!mediaFileService.exists(to)){
				result.setError(message(request, "error.move"));
			}
		}else{
			result.setError(message(request, "error.directory"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		
		if(result.getError()==null){
			if(mediaFileService.move(from, to)){
				result.setSuccess(message(request, "text.move"));
			}else{
				result.setError(message(request, "error.move"));
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/copy")
	public @ResponseBody ActionResult copy(@RequestParam(value="path", required=false) String path,
			@RequestParam(value="name", required=false) String name, HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		if(StringUtils.isNotEmpty(path)){
			if(name==null || name.length()<3 || name.length()> 255){
				result.setError(message(request, "error.filename"));
			}else if(!mediaFileService.exists(path) || name=="/"){
				result.setError(message(request, "error.copy"));
			}
		}else{
			result.setError(message(request, "error.select"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		
		if(result.getError()==null){
			if(mediaFileService.copy(path, name)){
				result.setSuccess(message(request, "text.copy"));
			}else{
				result.setError(message(request, "error.copy"));
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/folders")
	public @ResponseBody String getFolders(){
		StringBuilder strBuilder = new StringBuilder();
		for(String folder: mediaFileService.getFolders()){
			strBuilder.append("<option value=\"").append(folder).append("\">")
			.append(folder).append("</option>");
		}
		return strBuilder.toString();
	}
	
	
	@RequestMapping(value="/rename")
	public @ResponseBody ActionResult rename(@RequestParam(value="path", required=false) String path,
			@RequestParam(value="name", required=false) String name, HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		if(StringUtils.isNotEmpty(path)){
			if(name==null || name.length()<3 || name.length()> 255){
				result.setError(message(request, "error.filename"));
			}else if(!mediaFileService.exists(path) || name=="/"){
				result.setError(message(request, "error.rename"));
			}
		}else{
			result.setError(message(request, "error.select"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		
		if(result.getError()==null){
			if(mediaFileService.rename(path, name)){
				result.setSuccess(message(request, "text.rename"));
			}else{
				result.setError(message(request, "error.rename"));
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/upload")
	public @ResponseBody ActionResult upload(@RequestParam(value="directory", required=false) String directory, 
			@RequestParam(value="image") MultipartFile multipartFile, HttpServletRequest request){
		
		ActionResult result = new ActionResult();
		
		String fileName = null;
		
		if(StringUtils.isNotEmpty(directory)){
			
			if(!mediaFileService.isDirectory(directory)){
				result.setError(message(request, "error.directory"));
			}else{
				if(multipartFile.isEmpty()){
					result.setError(message(request, "error.file"));
				}else{
					String originalName = multipartFile.getOriginalFilename();
					fileName = FilenameUtils.getName(originalName);
					String fileExt = FilenameUtils.getExtension(originalName);
					long fileSize = multipartFile.getSize();
					if(fileName==null || fileName.length()<3 || fileName.length()>255){
						result.setError(message(request, "error.filename"));
					}else if(fileSize > getSettingService().getEnvironment().getProperty("fileUpload.maxSize", Long.class)){
						result.setError(message(request, "error.fileSize"));
					}else if(!FileFilters.MEDIA_TYPES.contains(fileExt.toLowerCase())){
						result.setError(message(request, "error.fileType"));
					}
				}
			}
		}else{
			result.setError(message(request, "error.directory"));
		}
		
		if(!UserPermissions.canModify("common/filemanager")){
			result.setError(message(request, "error.permission"));
		}
		
		if(result.getError()==null){
			if(mediaFileService.upload(multipartFile, PathUtils.ensureEndingFileSeparator(directory)+fileName)){
				result.setSuccess(message(request, "text.uploaded"));
			}else{
				result.setError(message(request, "error.uploaded"));
			}
		}
		return result;
	}
}
