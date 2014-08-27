/**
 * 
 */
package com.mhp.service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.mhp.dao.user.UserDao;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
	private Logger logger = ApplicationUtilities.getLogger(CustomLogoutSuccessHandler.class);;
	
    UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	@Override
    public void onLogoutSuccess(HttpServletRequest request,
                    HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {

            logger.debug("Inside onLogoutSuccess");
           
            if(null != authentication) {
                    authentication=null;
                    request.getSession().invalidate();
                    response.sendRedirect("login.htm");
            }
            else
                    response.sendRedirect("welcome.htm");
    }

}
