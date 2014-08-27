/**
 * 
 */
package com.mhp.domain.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mhp.domain.common.CommonDetails;

/**
 * @author kiwitech
 *
 */
@Entity
@Table(name="user_details")
public class UserDTO extends CommonDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919219542106847159L;
	private String username;
	private String userDisplayNameFirst;
	private String userDisplayNameMiddle;
	private String userDisplayNameLast;

	//    @NotBlank
	//    @Email
	private String emailId;
	private String password;

	//editable fields
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;


	private boolean credentialsExpired;                                     //additional field for checking of credentials expiry
	private String[] role;
	private Integer count;

	private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();


	@Id
	@Column(name = "USER_ID", length = 20)
	public String getUsername() {
		return username;
	}

	@Column(name = "USER_NAME_FIRST", length = 40, nullable = false)
	public String getUserDisplayNameFirst() {
		return userDisplayNameFirst;
	}

	@Column(name = "USER_NAME_MIDDLE", length = 40)
	public String getUserDisplayNameMiddle() {
		return userDisplayNameMiddle;
	}

	@Column(name = "USER_NAME_LAST", length = 40)
	public String getUserDisplayNameLast() {
		return userDisplayNameLast;
	}

	@Column(name = "EMAIL_ID", nullable=false, length = 50)
	public String getEmailId() {
		return emailId;
	}

	@Basic
	@Column(name = "HASHED_PASSWORD", length = 500, nullable = false)
	public String getPassword() {
		return password;
	}

	@Column(name = "IS_ACNT_NON_EXPD", nullable = false, columnDefinition = "smallint default 1")
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Column(name = "IS_ACNT_NON_LOCKD", nullable = false, columnDefinition = "smallint default 1")
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Column(name = "IS_CRED_NON_EXPD", nullable = false, columnDefinition = "smallint default 1")
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Column(name = "IS_ENABLED", nullable = false, columnDefinition = "smallint default 1")
	public boolean isEnabled() {
		return isEnabled;
	}

	@ManyToMany(targetEntity = RoleDTO.class, cascade = CascadeType.DETACH)
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "user_role_mapping",
	joinColumns = @JoinColumn(name = "URM_USER_ID"),
	inverseJoinColumns = @JoinColumn(name = "URM_ROLE_ID"))
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Column(name = "COUNT", length=10)
	public Integer getCount() {
		return count;
	}




	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setUserDisplayNameFirst(String userDisplayNameFirst) {
		this.userDisplayNameFirst = userDisplayNameFirst;
	}

	public void setUserDisplayNameMiddle(String userDisplayNameMiddle) {
		this.userDisplayNameMiddle = userDisplayNameMiddle;
	}

	public void setUserDisplayNameLast(String userDisplayNameLast) {
		this.userDisplayNameLast = userDisplayNameLast;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	@Transient
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", userDisplayNameFirst="
				+ userDisplayNameFirst + ", userDisplayNameMiddle="
				+ userDisplayNameMiddle + ", userDisplayNameLast="
				+ userDisplayNameLast + ", emailId=" + emailId + ", password="
				+ password + ", isAccountNonExpired=" + isAccountNonExpired
				+ ", isAccountNonLocked=" + isAccountNonLocked
				+ ", isCredentialsNonExpired=" + isCredentialsNonExpired
				+ ", isEnabled=" + isEnabled + ", credentialsExpired="
				+ credentialsExpired + ", role=" + Arrays.toString(role)
				+ ", count=" + count + ", authorities=" + authorities + "]";
	}

	public static final String USER_ID="username";
	public static final String EMAIL_ID="emailId";
}