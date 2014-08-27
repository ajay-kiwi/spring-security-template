/**
 * 
 */
package com.mhp.service.user;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	private Logger logger = ApplicationUtilities.getLogger(CustomAuthenticationProvider.class);
	
	UserService userDetailsService;
	public UserService getUserDetailsService() {
		return userDetailsService;
	}
	public void setUserDetailsService(UserService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
	private ShaPasswordEncoder passwordEncoder;
	
	public void setPasswordEncoder(ShaPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.userNotFoundEncodedPassword = passwordEncoder.encodePassword(USER_NOT_FOUND_PASSWORD, null);
	}
	public ShaPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	private String userNotFoundEncodedPassword;
	
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		logger.debug("ENTERED IN CUSTOM AUTHENTICATION PROVIDER " + username);
		UserDTO loadedUser = null;
		
		try {
			loadedUser = (UserDTO) userDetailsService.loadUserByUsername(username);
		} 
		catch (UsernameNotFoundException notFound) {
			Boolean matchingResult = false;
            if(authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
                matchingResult = passwordEncoder.isPasswordValid(userNotFoundEncodedPassword, presentedPassword, null);
                System.out.println(matchingResult?"Passwords are matched":"Passwords are not matching");
            }
            
            if(matchingResult) {
            	throw new UsernameNotFoundException("Username provided doesn't exists in database.");
            }
            else {
            	throw new UsernameNotFoundException("Password is incorrect.");
            }
//            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
		catch (Throwable e) {
			logger.debug(e.getMessage(),e);
		}

        if (null == loadedUser) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
	}

}
