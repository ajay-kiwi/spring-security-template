/**
 * 
 */
package com.mhp.util.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * @author kiwitech
 *
 */
public class CustomAuthenticationManager implements AuthenticationManager{

	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
    
    static {
      AUTHORITIES.add(new GrantedAuthorityImpl(ApplicationConstants.ROLE_GENERAL_USER));
    }
   
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
      if (auth.getName().equals(auth.getCredentials())) {
        return new UsernamePasswordAuthenticationToken(auth.getName(),
          auth.getCredentials(), AUTHORITIES);
      }
      throw new BadCredentialsException("Bad Credentials");
    }
}