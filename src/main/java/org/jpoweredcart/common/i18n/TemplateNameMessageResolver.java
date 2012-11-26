package org.jpoweredcart.common.i18n;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.MessageResolution;

public class TemplateNameMessageResolver extends AbstractMessageResolver{
	
	public static final String TEMPLATE_NAME_ATTR = TemplateNameMessageResolver.class.getName()+".TPL"; 
	
	@Override
	public MessageResolution resolveMessage(Arguments arguments, String key,
			Object[] messageParameters) {
		
		WebContext webContext = (WebContext)arguments.getContext();
		String templateName = arguments.getTemplateResolution().getTemplateName();
		templateName = StringUtils.substringBeforeLast(templateName, ".");
		
		webContext.getHttpServletRequest().setAttribute(TEMPLATE_NAME_ATTR, templateName);
		
		return super.resolveMessage(arguments, key, messageParameters);
	}
	
	@Override
	public String[] getMessagePaths(HttpServletRequest request){
		
		String templateName = (String)request.getAttribute(TEMPLATE_NAME_ATTR);
		
		return new String[]{templateName};
	}
	
}
