package com.lostacos.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lostacos.R;
import com.lostacos.controller.LanguageController;
import com.lostacos.util.network.NetworkChangeListener;
import com.lostacos.controller.LoginController;

public class LoginActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    LoginController loginController = new LoginController();

    EditText email_input, password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LanguageController.SelectLanguage(this);

        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);

        Button b_login = findViewById(R.id.b_login);
        b_login.setOnClickListener(view -> loginController.login(this, email_input.getText().toString(), password_input.getText().toString()));

        TextView b_register = (TextView) findViewById(R.id.b_register);
        b_register.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}