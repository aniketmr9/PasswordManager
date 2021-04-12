package com.aniket.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aniket.passwordmanager.constants.PasswordManagerConstant;
import com.aniket.passwordmanager.model.User;
import com.aniket.passwordmanager.service.PasswordManagerService;
import com.aniket.passwordmanager.service.PasswordManagerServiceImpl;

import java.io.*;
import java.util.Properties;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameReg;
    private EditText passwordReg;
    private EditText passwordConfirmReg;
    private Button createUser;
    private TextView regError;
    private PasswordManagerService passwordManagerService = new PasswordManagerServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameReg = (EditText)findViewById(R.id.usernameReg);
        passwordReg = (EditText)findViewById(R.id.passwordReg);
        passwordConfirmReg = (EditText)findViewById(R.id.passwordConfirmReg);
        createUser = (Button) findViewById(R.id.createUser);
        regError = (TextView)findViewById(R.id.regError);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("register activity");
                if(passwordReg.getText().toString().equals(passwordConfirmReg.getText().toString())) {
                    Context context = getBaseContext();
                    User user = new User(usernameReg.getText().toString(), passwordReg.getText().toString());
                    String userCreatedMsg = passwordManagerService.createUser(user, context);
                    System.out.println("OP:" + userCreatedMsg);
                    if (!userCreatedMsg.equals(PasswordManagerConstant.USER_CREATED)) {
                        regError.setText(userCreatedMsg);
                    } else {
                        regError.setText(userCreatedMsg);
                        regError.setTextColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this, AccountsActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                        }, 2000);   //5 seconds
                    }
                }else
                {
                    regError.setText("Passwords do not match");
                }
            }
        });
    }

}