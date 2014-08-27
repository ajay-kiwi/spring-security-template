/**
 * 
 */
package com.mhp.service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	private Logger logger = ApplicationUtilities.getLogger(CustomAuthenticationFailureHandler.class);

	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Value(value="${enableAcccountLocking}")
	String enableAcccountLocking;

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, AuthenticationException authException)
					throws IOException, ServletException {

		UserDTO detailsDTO = (UserDTO)authException.getExtraInformation();
		if(null != detailsDTO) {
			if(null == detailsDTO.getCount()) {
				String userId = detailsDTO.getUsername();
				detailsDTO.setCount(0);
				try {
					userService.updateUserDetails(detailsDTO);
				} catch (Throwable e) {
					e.printStackTrace();
					logger.debug(e.getMessage(),e);
				}
				detailsDTO = (UserDTO)userService.loadUserByUsername(userId);
			}


//			Properties propertyFile = ApplicationUtilities.returnPropertyFileObject(ApplicationConstants.switchesPropertyFilePath, CustomAuthenticationFailureHandler.class);
//			String enableAcccountLocking = propertyFile.getProperty("enableAcccountLocking");
			if(enableAcccountLocking.equalsIgnoreCase("1")) {
				logger.debug("Account locking feature enabled");
				userService.accountLocked(detailsDTO);        
			}
		}
		httpServletResponse.sendRedirect("loginFailed.htm");            
		//          super.onAuthenticationFailure(httpServletRequest, httpServletResponse, authException);
	}

}