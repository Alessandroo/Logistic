package com.logistic.model.systemunits.entities;

import com.logistic.model.Grant;

import java.sql.Date;

public class User extends Entity {
	private String name;

	private String login;

	private String password;	

	private String email;

	private String telephone;

	private Grant grant;

	private String group;

	private Date createDate;

	// ----- Getters -----
	public String getName() {
		return name;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getTelephone() {
		return telephone;
	}
	public Grant getGrant() {
		return grant;
	}
	public String getGroup() {
		return group;
	}

	// ----- Setters -----
	public void setName(String name) {
		this.name = name;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	public void setGrant(Grant grant) {
		this.grant = grant;
	}
    public void setGroup(String group) {
        this.group = group;
    }
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}