/**
 * 
 */
package com.mhp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author kiwitech
 *
 */
@Entity
@Table(name="role_details")
public class RoleDTO implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 509258382732291752L;
	private String roleDesc;
	private String roleId;
	private int rolePriority;

	@Id
	@Column(name="ROLE_ID",length=50)
	public String getAuthority() {
		return roleId;
	}

	@Column(name="ROLE_DESC",length=100)
	public String getRoleDesc() {
		return roleDesc;
	}
	@Column(name="ROLE_PRIORITY",nullable=false)
	public int getRolePriority() {
		return rolePriority;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Transient
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setAuthority(String roleId){
		this.setRoleId(roleId);
	}

	public void setRolePriority(int rolePriority) {
		this.rolePriority = rolePriority;
	}

	@Override
	public String toString() {
		return "RoleDetailsDTO [roleDesc=" + roleDesc + ", roleId=" + roleId
				+ ", rolePriority=" + rolePriority + "]";
	}
}