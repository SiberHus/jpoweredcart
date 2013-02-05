package org.jpoweredcart.common.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jpoweredcart.admin.model.user.UserAdminModel;
import org.jpoweredcart.common.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


/**
 * 
 * @author Hussachai Puripunpinyo
 *
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	private static final Logger log = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	public static final String USER_ID_SESSION = "_USER_ID";
	public static final String USER_ROLE_SESSION = "_USER_ROLE";
	
	@Inject
	private UserAdminModel userModel;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userModel.getUserByUsername(auth.getName());
			if(user.getStatus()==0){
				log.info("Banned user:{} tried to login", user.getUsername());
				//invalidate authentication
				SecurityContextHolder.getContext().setAuthentication(null);
			}
			request.getSession().setAttribute(USER_ID_SESSION, user.getId());
//			request.getSession().setAttribute(USER_ROLE_SESSION, account.getRole().getDisplayName());
//			UserInfo userInfo = account.getInfo();
//			userInfo.setCurrentIp(request.getRemoteAddr());
//			userService.updateInfo(userInfo);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
