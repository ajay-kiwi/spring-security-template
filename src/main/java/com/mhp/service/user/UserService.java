/**
 * 
 */
package com.mhp.service.user;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.mhp.dao.user.UserDao;
import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationConstants;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class UserService implements UserDetailsService, UserDetailsManager {

	private Logger logger = ApplicationUtilities.getLogger(UserService.class);

	Properties propertyFile = ApplicationUtilities.returnPropertyFileObject(ApplicationConstants.switchesPropertyFilePath, UserService.class);
	

	UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserDetails loadUserByUsername(String loginString)
			throws UsernameNotFoundException {

		UserDTO userDetails = null;

		//Prepare criteria for fetching user details
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserDTO.class);
		if(ApplicationUtilities.isValidAccordingToRegex(loginString, ApplicationConstants.REGEX_FOR_EMAIL))
			detachedCriteria.add(Restrictions.eq(UserDTO.EMAIL_ID, loginString));
		else
			detachedCriteria.add(Restrictions.eq(UserDTO.USER_ID, loginString));

		//fetching user according to criteria
		try {
			@SuppressWarnings("rawtypes")
			List tempList = userDao.getObjectAccordingToCriteria(detachedCriteria);
			if(ApplicationUtilities.isValidList(tempList))
				userDetails = (UserDTO) tempList.get(0);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			if(null == userDetails){
				throw new UsernameNotFoundException("User doesn't exists in database.");
			}
		}
		
		
		//for automatic credentials expiry, based on switch defined in properties
		String enableCredentialAutoExpiry = propertyFile.getProperty("enableCredentialAutoExpiry");
		System.out.println(enableCredentialAutoExpiry);
		if(enableCredentialAutoExpiry.equalsIgnoreCase("1")) {
			logger.debug("Credential expiry check enabled");
			credentialsExpired(userDetails);        
		}
		return userDetails; 
	}


	public UserDTO credentialsExpired(UserDTO userDetailsDTO){

		if(null == userDetailsDTO)
			return userDetailsDTO;

		logger.debug("inside method credentialsExpired for user: "
				+ userDetailsDTO.getUserDisplayNameFirst()+userDetailsDTO.getUserDisplayNameLast()+"("+userDetailsDTO.getUsername()+")");


		Integer numberOfDaysForExpiry=null;
		try {
			String numberOfDaysForCredentialExpiry = propertyFile.getProperty("numberOfDaysForCredentialExpiry");
			numberOfDaysForExpiry = Integer.valueOf(numberOfDaysForCredentialExpiry);
		}
		catch(Exception e) {
			logger.debug(e.getMessage(),e);
		}

		if(null == numberOfDaysForExpiry)
			numberOfDaysForExpiry = 0;

		if(userDetailsDTO.getUpdatedDate() == null){
			Date createddate = userDetailsDTO.getCreatedDate();
			Date newdate = new Date();

			long milliseconds1 = createddate.getTime();
			long milliseconds2 = newdate.getTime();

			long diff = milliseconds2 - milliseconds1;
			long diffdays = diff / (24 * 60 * 60 * 1000);

			if(diffdays > numberOfDaysForExpiry){
				userDetailsDTO.setCredentialsExpired(true);
			}
		}
		else if(userDetailsDTO.getUpdatedDate() != null){
			Date updateddate = userDetailsDTO.getUpdatedDate();
			Date newdate = new Date();

			long milliseconds1 = updateddate.getTime();
			long milliseconds2 = newdate.getTime();


			long diff = milliseconds2 - milliseconds1;
			long diffdays = diff / (24 * 60 * 60 * 1000);

			if(diffdays > numberOfDaysForExpiry){
				userDetailsDTO.setCredentialsExpired(true);
			}
		}
		return userDetailsDTO;
	}

	public void accountLocked(UserDTO detailsDTO){
		if(null != detailsDTO.getCount())
			detailsDTO.setCount(detailsDTO.getCount().intValue()+1);

		logger.debug("Login Failure occurred due to wrong password entered." +
				" A total of "+detailsDTO.getCount()+" times a wrong password is entered for userID: " + detailsDTO.getUsername());

		String numberOfAttemptsForAccountLocking = null;
		try {
			numberOfAttemptsForAccountLocking = propertyFile.getProperty("numberOfAttemptsForAccountLocking");
			if(detailsDTO.getCount().intValue() >= Integer.valueOf(numberOfAttemptsForAccountLocking).intValue()){
				detailsDTO.setAccountNonLocked(false);
				logger.debug("Account for userId '" + detailsDTO.getUsername()+"' is locked.");
			}
			userDao.updateUserDetails(detailsDTO);
		}
		catch(Throwable e) {
			logger.debug(e.getMessage(),e);
		}
	}

	public void changePassword(String arg0, String arg1) {}

	public void updateUserDetails(UserDTO detailsDTO){
		try {
			userDao.updateUserDetails(detailsDTO);
		} catch (Throwable e) {
			logger.debug(e.getMessage(),e);
		}
	}
	
	/********************** Implemented methods of "UserDetailsManager" Interface ******************/ 
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	/************************************************************************************************/

}