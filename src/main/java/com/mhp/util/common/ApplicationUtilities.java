/**
 * 
 */
package com.mhp.util.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mhp.domain.common.CommonDetails;
import com.mhp.domain.user.RoleDTO;
import com.mhp.domain.user.UserDTO;

/**
 * @author kiwitech
 *
 */
public class ApplicationUtilities {

    private static Logger logger = ApplicationUtilities.getLogger(ApplicationUtilities.class);

	public static UserDTO returnUserObject() {
		return (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static void setCommonDetails(CommonDetails commonDetailsDTO, boolean isNew){
		if(isNew){
			commonDetailsDTO.setCreatedDate(new Date());
			if(null != returnUserObject())
				commonDetailsDTO.setStrCreatedAuthor(returnUserObject().getUsername());
			else
				commonDetailsDTO.setStrCreatedAuthor("Default");
			commonDetailsDTO.setVersion(1);
		}
		else {
			commonDetailsDTO.setUpdatedDate(new Date());
			if(null != returnUserObject())
				commonDetailsDTO.setStrCreatedAuthor(returnUserObject().getUsername());
			else
				commonDetailsDTO.setStrCreatedAuthor("Default");
			commonDetailsDTO.setVersion(commonDetailsDTO.getVersion()+1);
		}
	}

	public static void setCommonDetailsForNonLogin(CommonDetails commonDetailsDTO, boolean isNew){
		if(isNew){
			commonDetailsDTO.setCreatedDate(new Date());
			commonDetailsDTO.setStrCreatedAuthor("Default");
			commonDetailsDTO.setVersion(1);
		}
		else {
			commonDetailsDTO.setUpdatedDate(new Date());
			commonDetailsDTO.setStrCreatedAuthor("Default");
			commonDetailsDTO.setVersion(commonDetailsDTO.getVersion()+1);
		}
	}

	public static boolean checkContainsValue(@SuppressWarnings("rawtypes") Map entityMap, Object keyValue ){
		return entityMap.containsKey(keyValue);
	}

	public static boolean isValidList(List<?> objectList){
		boolean isValid  = false;
		if(null != objectList && objectList.size() > 0)
			isValid = true;
		return isValid;
	}

	public static boolean checkIfNullOrBlank(String strToCheck){
		if(null == strToCheck || "".equalsIgnoreCase(strToCheck))
			return true;
		return false;
	}

	public static Properties returnPropertyFileObject(String propertyFilePath, @SuppressWarnings("rawtypes") Class entityClass) {
		Properties properties = new Properties();
		try {
			System.out.println(entityClass.getCanonicalName());
			System.out.println(propertyFilePath);
			properties.load(entityClass.getResourceAsStream(propertyFilePath));    
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return properties;
	}

	public static String returnHash(String password) {
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		return passwordEncoder.encodePassword(password, null);
	}

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class entityClass) {
		return Logger.getLogger(entityClass);
	}

	public static boolean isValidAccordingToRegex(String stringToVerify, String regexString) {
		Pattern pattern = Pattern.compile(regexString);
		Matcher matcher = pattern.matcher(stringToVerify);
		return matcher.matches();
	}
	
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
		Map<K,V> sortedMap = new LinkedHashMap<K,V>();
		if(null!=map){
			List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());

			Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});

			for(Map.Entry<K,V> entry: entries){
				sortedMap.put(entry.getKey(), entry.getValue());
			}
		}

		return sortedMap;
	}

	public static int dobToAgeConvertor(Date dob){
		logger.debug("Inside dobToAgeConvertor method");
		LocalDate birthDate = new LocalDate(dob);
		LocalDate currentDate = new LocalDate();
		Years age = Years.yearsBetween(birthDate, currentDate);
		return age.getYears();
	}
	
	public static int getMonthDifference(Date startDate, Date endDate){
		logger.debug("Inside getMonthDifference method");

		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(startDate);
		Calendar endDateCal = Calendar.getInstance();
		endDateCal.setTime(endDate);
		int yearDiff = endDateCal.get(Calendar.YEAR)-startDateCal.get(Calendar.YEAR) ;
		int monthDiff = endDateCal.get(Calendar.MONTH)-startDateCal.get(Calendar.MONTH) ;
		logger.debug(userReadableDateFormat(startDate)+" to "+userReadableDateFormat(endDate)+" = "+(12*yearDiff+monthDiff));
		return 12*yearDiff+monthDiff;
	}
	
	public static String userReadableDateFormat(Date inputDate){
		SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
		return sd.format(inputDate);
	}

	public static String getCurrentDateInString(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();        
		String reportDate = df.format(today);
		return reportDate;
	}
	
	public static void reloadSessionUserObject(UserDTO user) {
		AuthenticationManager authenticationManager = new CustomAuthenticationManager();                
		Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		Authentication result = authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}
	
	public static Boolean[] userHasRole(UserDTO detailsDTO, String[] roles) {
		Boolean[] resultantArray = new Boolean[roles.length];
		for(int i=0; i<roles.length; i++) {
			for(GrantedAuthority authority : detailsDTO.getAuthorities()) {
				if(roles[i].equals(((RoleDTO)authority).getRoleId()))
					resultantArray[i] = true;
				else
					resultantArray[i] = false;
			}
		}
		return resultantArray;
	}

}