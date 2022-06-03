package com.lostacos.view;

import static com.lostacos.util.DataFormat.setFormatDateInString;
import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.getPreference;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.lostacos.R;
import com.lostacos.util.network.NetworkChangeListener;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TextView name;
    TextView email;
    TextView birthday;
    TextView cpf;
    TextView phone;
    TextView createdAt;
    TextView office;

    ImageView crown;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        name = (TextView) findViewById(R.id.name);
        name.setText(getPreference(this, "name"));

        email = (TextView) findViewById(R.id.email);
        email.setText(getPreference(this, "email"));

        cpf = (TextView) findViewById(R.id.cpf);
        cpf.setText(getPreference(this, "cpf"));

        phone = (TextView) findViewById(R.id.phone);
        phone.setText(getPreference(this, "phone"));

        birthday = (TextView) findViewById(R.id.birthday);
        birthday.setText(setFormatDateInString(getPreference(this, "birthday")));

        createdAt = (TextView) findViewById(R.id.createdAt);
        createdAt.setText(setFormatDateInString(getPreference(this, "createdAt")));

        office = (TextView) findViewById(R.id.office);

        if(getPreference(this, "master").equals("true")){
            ImageView crown = findViewById(R.id.user);
            crown.setImageResource(R.drawable.ic_crown_solid);
            office.setText(getStringByName(this, "administrador"));
        }else{
            office.setText(getStringByName(this, "cliente"));
        }

        ImageView b_back = findViewById(R.id.b_back);
        b_back.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
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
