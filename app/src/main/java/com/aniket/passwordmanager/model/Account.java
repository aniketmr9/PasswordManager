package com.aniket.passwordmanager.model;

public class Account {
	private String accountName;
	private String username;
	private String password;

	public Account(String accountName, String username, String password) {
		this.accountName = accountName;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
