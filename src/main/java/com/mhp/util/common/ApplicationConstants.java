/**
 * 
 */
package com.mhp.util.common;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author kiwitech
 *
 */
@Component(value="applicationConstants")
public class ApplicationConstants {

	public static String switchesPropertyFilePath="/switches.properties";

	public static final String REGEX_FOR_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	public static final String ROLE_GENERAL_USER="ROLE_USER";
	
	@PostConstruct
	private void loadConstantValues() {
		//for loading values of constants at starting time of server
	}

}
