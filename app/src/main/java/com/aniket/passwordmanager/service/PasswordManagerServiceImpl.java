package com.aniket.passwordmanager.service;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.aniket.passwordmanager.constants.PasswordManagerConstant;
import com.aniket.passwordmanager.model.Account;
import com.aniket.passwordmanager.model.User;
import com.aniket.passwordmanager.utility.BCryptEncryptor;
import com.aniket.passwordmanager.utility.FileReaderWriter;

import java.io.File;
import java.util.List;

public class PasswordManagerServiceImpl implements PasswordManagerService {

	@Override
	public String authenticateUser(User user, Context context) {
		File file = new File(context.getFilesDir(), user.getUsername());
		System.out.println("Auth file:" +file.getAbsolutePath());
		if(!file.exists()) {
			return PasswordManagerConstant.INCORRECT_USERNAME;
		}
		return FileReaderWriter.authenticate(user, new File(file,user.getUsername()+".csv"));
	}

	@Override
	public String createUser(User user, Context context) {
		boolean userCreated = false;
		User userReg = new User(user.getUsername(), BCryptEncryptor.encrypt(user.getPassword()));
		File file = new File(context.getFilesDir(), userReg.getUsername());
		if(file.exists()) {
			return PasswordManagerConstant.USER_ALREADY_EXIST;
		}
		file.mkdir();
		userCreated = FileReaderWriter.newUser(new File(file,userReg.getUsername()+".csv"), userReg);
		return userCreated == true ? PasswordManagerConstant.USER_CREATED : PasswordManagerConstant.USER_CREATION_ERROR;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public List<Account> getAccounts(User user, Context context) {
		File file = new File(context.getFilesDir(), user.getUsername());
		return FileReaderWriter.fileReader(user, new File(file,user.getUsername()+".csv"));
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public String addAccounts(User user, Account account, Context context) {
		boolean accountAdded = false;
		File file = new File(context.getFilesDir(), user.getUsername());
		accountAdded = FileReaderWriter.fileWriter(user, new File(file,user.getUsername()+".csv"), account);
		return  accountAdded == true ? PasswordManagerConstant.ACCOUNT_ADDED_SUCCESS : PasswordManagerConstant.GENERIC_ERROR;
	}

}
