package com.hzu.homework.sysback.sysaccount.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_account")
public class SysAccountModel implements Serializable {

	private static final long serialVersionUID = 1482055695347272535L;
	
	@Id
	@GeneratedValue
	private long uuid;
	
	private String loginName;
	
	private String password;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getUuid() {
		return uuid;
	}
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
