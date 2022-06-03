package com.lostacos.view;

import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.lostacos.R;
import com.lostacos.util.network.NetworkChangeListener;

public class HomeActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(getStringByName(this, "ola")+ ", " + getPreference(this, "name"));

        TextView b_exit = findViewById(R.id.b_exit);
        b_exit.setOnClickListener(view -> {
            deletePreference(this, "token");
            deletePreference(this, "id");
            deletePreference(this, "name");
            deletePreference(this, "email");
            startActivity(new Intent(this, LoginActivity.class));
        });

        CardView b_reservations = findViewById(R.id.b_reservations);
        b_reservations.setOnClickListener(view -> startActivity(new Intent(this, ReserveActivity.class)));

        CardView b_menu = findViewById(R.id.b_menu);
        b_menu.setOnClickListener(view -> startActivity(new Intent(this, MenuActivity.class)));

        CardView b_profile = findViewById(R.id.b_profile);
        b_profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));

        CardView b_about = findViewById(R.id.b_about);
        b_about.setOnClickListener(view -> startActivity(new Intent(this, AboutActivity.class)));
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
