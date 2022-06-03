package com.lostacos.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.lostacos.R;
import com.lostacos.controller.ReserveController;
import com.lostacos.util.network.NetworkChangeListener;

public class ReserveActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    ReserveController reserveController = new ReserveController();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        reserveController.LoadReserves(this);

        ImageView b_back = findViewById(R.id.b_back);
        b_back.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));

        Button my_reserves = findViewById(R.id.my_reserves);
        my_reserves.setOnClickListener(view -> startActivity(new Intent(this, MyReserves.class)));
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
