package com.dizae.models.entities;

import com.dizae.tools.Encrypter;

/**
 * @author Dola
 *
 */

public class User {
	
	
	private String name;
	private String email;
	private String password;
	private String cpf;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws Exception {
		this.password = Encrypter.SHA1(password);
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	

}
