package com.lostacos.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.lostacos.R;
import com.lostacos.controller.RegisterController;
import com.lostacos.util.network.NetworkChangeListener;

public class RegisterActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    RegisterController registerController = new RegisterController();

    EditText name_input, phone_input, email_input, cpf_input, birthday_input, password_input, confirmPassword_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_input = (EditText) findViewById(R.id.name_input);
        phone_input = (EditText) findViewById(R.id.phone_input);
        email_input = (EditText) findViewById(R.id.email_input);
        cpf_input = (EditText) findViewById(R.id.cpf_input);
        birthday_input = (EditText) findViewById(R.id.birthday_input);
        password_input = (EditText) findViewById(R.id.password_input);
        confirmPassword_input = (EditText) findViewById(R.id.confirmPassword_input);

        Button b_register = findViewById(R.id.b_register);
        b_register.setOnClickListener(view -> registerController.register(this, name_input.getText().toString(), phone_input.getText().toString(), email_input.getText().toString(), cpf_input.getText().toString(), birthday_input.getText().toString(), password_input.getText().toString(), confirmPassword_input.getText().toString()));

        Button b_back = findViewById(R.id.b_back);
        b_back.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
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