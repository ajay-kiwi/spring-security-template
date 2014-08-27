/**
 * 
 */
package com.mhp.dao.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.mhp.domain.user.UserDTO;
import com.mhp.util.common.ApplicationUtilities;

/**
 * @author kiwitech
 *
 */
public class UserDao{

	private Logger logger = ApplicationUtilities.getLogger(UserDao.class);

	public UserDao() {
		System.out.println("UserDao instantiated");
	}
	
	public SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
        return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	}


	public UserDTO findByUserName(String username) {
		return null;
	}

	public List<?> getObjectAccordingToCriteria(DetachedCriteria detachedCriteria) {
		logger.debug("inside getObjectAccordingToCriteria");
		Session session = sessionFactory.openSession();
		List<?> dtoList = detachedCriteria.getExecutableCriteria(session).list();
		session.close();
		return dtoList;
	}

	public void updateUserDetails(UserDTO userDetailsDTO) {
		logger.debug("in method updateUserDetails: " + userDetailsDTO.getUsername());
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(userDetailsDTO);
		session.close();
	}

}
