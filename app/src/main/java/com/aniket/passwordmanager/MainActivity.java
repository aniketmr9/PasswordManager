package com.aniket.passwordmanager;

import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private TextView loginMsg;
    private PasswordManagerService passwordManagerService = new PasswordManagerServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PasswordManagerConstant passwordManagerConstant = new PasswordManagerConstant(getApplicationContext());
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        loginMsg = (TextView)findViewById(R.id.loginMsg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("login");
                Context context = getBaseContext();
                User user = new User(username.getText().toString(), password.getText().toString());
                System.out.println(user);
                String userLoginMsg = passwordManagerService.authenticateUser(user, context);
                System.out.println(userLoginMsg);
                if(!userLoginMsg.equals(PasswordManagerConstant.USER_AUTHENTICATED)){
                    loginMsg.setText(userLoginMsg);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, AccountsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("register");
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}