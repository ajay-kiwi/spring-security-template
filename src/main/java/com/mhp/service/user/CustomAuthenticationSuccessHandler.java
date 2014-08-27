/**
 * 
 */
package com.mhp.service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mhp.dao.user.UserDao;
import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private Logger logger = ApplicationUtilities.getLogger(CustomAuthenticationSuccessHandler.class);

	UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void onAuthenticationSuccess(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse,
			Authentication authentication) throws IOException,
			ServletException {

		HttpSession session=httpservletrequest.getSession();
		String urlToRedirect="";
//		logger.debug("session getid = "+session.getId());
//		logger.debug("requested session id = "+httpservletrequest.getRequestedSessionId());
		UserDTO requestDTO = ApplicationUtilities.returnUserObject();    
		if(null != requestDTO.getCount() && requestDTO.getCount() >= 0){
			requestDTO.setCount(1);
			try {
				userDao.updateUserDetails(requestDTO);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}

		if(null != requestDTO){
			if(requestDTO.isCredentialsExpired() == true){
				logger.debug("User with userId '" + requestDTO.getUsername() +
						"' logged in successfully but have to change the password as the credentials are expired.");
				httpservletresponse.sendRedirect("changepassword.htm");
			}
			else{
				logger.debug("User with userId '" + requestDTO.getUsername() +"' logged in successfully.");
				urlToRedirect="authenticateSuccess.htm";
			}

			httpservletresponse.sendRedirect(urlToRedirect);
		}
	}

}
