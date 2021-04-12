package com.aniket.passwordmanager;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aniket.passwordmanager.constants.PasswordManagerConstant;
import com.aniket.passwordmanager.model.Account;
import com.aniket.passwordmanager.model.User;
import com.aniket.passwordmanager.service.PasswordManagerService;
import com.aniket.passwordmanager.service.PasswordManagerServiceImpl;

import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    private TableLayout accountsLayout;
    private EditText addAccountName;
    private EditText addUsername;
    private EditText addPassword;
    private Button addAccountBtn;
    private TextView addAccountErr;
    private PasswordManagerService passwordManagerService = new PasswordManagerServiceImpl();
    List<Account> accountsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        accountsLayout = (TableLayout) findViewById(R.id.accountsLayout); // Root ViewGroup in which you want to add textviews
        User user = (User) getIntent().getSerializableExtra("user");
        System.out.println("add acc:"+user);
        Context context = getBaseContext();
        accountsList = passwordManagerService.getAccounts(user, context);
        accountsList.forEach(account -> {
            TableRow accountRow=new TableRow(this);

            TextView accountName=new TextView(this);
            accountName.setText(account.getAccountName());
            accountName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            accountRow.addView(accountName);

            TextView accountUsername=new TextView(this);
            accountUsername.setText(account.getUsername());
            accountUsername.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            accountRow.addView(accountUsername);

            TextView accountPassword=new TextView(this);
            accountPassword.setText(account.getPassword());
            accountPassword.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            accountRow.addView(accountPassword);
            accountsLayout.addView(accountRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        });
        TableRow addAccount=new TableRow(this);

        addAccountName = new EditText(this);
        addAccountName.setTag("addAccountName");
        addAccountName.setHint("Account Name");
        addAccountName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        addAccount.addView(addAccountName);

        addUsername = new EditText(this);
        addUsername.setTag("addUsername");
        addUsername.setHint("Username");
        addUsername.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        addAccount.addView(addUsername);

        addPassword = new EditText(this);
        addPassword.setTag("addPassword");
        addPassword.setHint("Password");
        addPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        addPassword.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        addAccount.addView(addPassword);

        accountsLayout.addView(addAccount, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        addAccountBtn = (Button) findViewById(R.id.addAccountBtn);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(addAccountName.getText().toString()+":"+addUsername.getText().toString()+":"+addPassword.getText().toString());
                Account account = new Account(addAccountName.getText().toString(), addUsername.getText().toString(), addPassword.getText().toString());
                String accountAddMsg = passwordManagerService.addAccounts(user, account, context);
                if(!accountAddMsg.equals(PasswordManagerConstant.ACCOUNT_ADDED_SUCCESS)){
                    addAccountErr = (TextView)findViewById(R.id.addAccountErr);
                    addAccountErr.setText(accountAddMsg);
                }
                else{
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
}