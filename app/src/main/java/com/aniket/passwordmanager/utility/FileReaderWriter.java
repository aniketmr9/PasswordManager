package com.aniket.passwordmanager.utility;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.aniket.passwordmanager.constants.PasswordManagerConstant;
import com.aniket.passwordmanager.model.Account;
import com.aniket.passwordmanager.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReaderWriter {

    public static boolean newUser(File file, User userReg) {
        System.out.println(file.exists());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(userReg.getUsername() + "," + userReg.getPassword() + "\n");
            System.out.println("user :" + userReg);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean fileWriter(User user, File file, Account account) {
        System.out.println(file.exists());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(account.getAccountName()+ "," +account.getUsername()+ "," +AESEncryptDecrypt.encrypt(account.getPassword(), user.getPassword()) + "\n");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Account> fileReader(User user, File file) {
        System.out.println(file.exists());
        List<Account> accountList = new ArrayList<Account>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            String userLine[] = null;
            String accountLine[] = null;
            if ((line = reader.readLine()) != null) {
                userLine = line.split(",");
                if (userLine[0].equals(user.getUsername()) && (BCryptEncryptor.verify(user.getPassword(), userLine[1]))) {
                    System.out.println("valid");
                } else {
                    System.out.println("fail");
                }
            }
            while ((line = reader.readLine()) != null) {
                accountLine = line.split(",");
                Account account = new Account(accountLine[0], accountLine[1], AESEncryptDecrypt.decrypt(accountLine[2], user.getPassword()));
                accountList.add(account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static String authenticate(User user, File file) {
        System.out.println(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            String userLine[] = null;
            if ((line = reader.readLine()) != null) {
                userLine = line.split(",");
                if (userLine[0].equals(user.getUsername())) {
                    if (BCryptEncryptor.verify(user.getPassword(), userLine[1])) {
                        return PasswordManagerConstant.USER_AUTHENTICATED;
                    } else {
                        return PasswordManagerConstant.INCORRECT_PASSWORD;
                    }
                }
                else{
                    return PasswordManagerConstant.INCORRECT_USERNAME;
                }
            }
            else {
                return PasswordManagerConstant.ACCOUNT_NOT_EXIST;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PasswordManagerConstant.GENERIC_ERROR;
    }
}
