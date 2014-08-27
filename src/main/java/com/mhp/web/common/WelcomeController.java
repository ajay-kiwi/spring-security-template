/**
 * 
 */
package com.mhp.web.common;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
@Controller
public class WelcomeController {

	private Logger logger = ApplicationUtilities.getLogger(WelcomeController.class);

	
	@RequestMapping("/login.htm")
	public String getLoginPage() {
		logger.debug("Opening Login Page");
		return "auth/login";
	}

	@RequestMapping("/authenticateSuccess.htm")
	public ModelAndView getDashboard(HttpSession session) {
		
		ModelAndView modelAndView = new ModelAndView();
		UserDTO currentUserObject = ApplicationUtilities.returnUserObject();
		if(null == currentUserObject) {
			modelAndView.setViewName("auth/sessionExpired");
			logger.debug("Session is expired.");
		}
		else {
			session.setAttribute("user",currentUserObject);
			modelAndView.addObject("user", currentUserObject);
			modelAndView.setViewName("auth/welcome");
			logger.debug("Login Successfull. Redirecting to dashboard.");
		}
		return modelAndView;
	}     

	@RequestMapping("/sessionExpired.htm")
	public String sessionExpired() {
		return "auth/sessionExpired";
	}

	@RequestMapping("/accessDenied.htm")
	public String accessDenied() {
		return "auth/accessDenied";
	}
}