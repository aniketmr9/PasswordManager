package com.aniket.passwordmanager.service;



import android.content.Context;
import com.aniket.passwordmanager.model.Account;
import com.aniket.passwordmanager.model.User;

import java.util.List;

public interface PasswordManagerService {
	public String authenticateUser(User user, Context context);
	public String createUser(User user, Context context);
	public List<Account> getAccounts(User user, Context context);
	public String addAccounts(User user, Account account, Context context);
}
