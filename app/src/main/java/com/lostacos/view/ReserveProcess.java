package com.lostacos.view;

import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.getPreference;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lostacos.Model.Reserve;
import com.lostacos.R;
import com.lostacos.controller.ReserveController;
import com.lostacos.util.network.NetworkChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ReserveProcess extends AppCompatActivity {
    Spinner hour_spinner;
    Spinner people_spinner;

    TextView table_text;

    Reserve reserve = new Reserve();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.reserve_process);
        super.onCreate(savedInstanceState);

        hour_spinner = findViewById(R.id.hour);
        people_spinner = findViewById(R.id.people);

        table_text = findViewById(R.id.table_text);
        table_text.setText(getStringByName(this, "mesa") + " " + getPreference(this, "table") + "\n" + getPreference(this, "date"));

        List<String> hour = new ArrayList<>();
        for(int i=19; i<=23; i++){
            hour.add("" + i);
        }

        List<String> people = new ArrayList<>();
        for(int i=1; i<=8; i++){
            people.add("" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hour);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour_spinner.setAdapter(adapter);
        hour_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                reserve.setHour(hour_spinner.getAdapter().getItem(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, people);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        people_spinner.setAdapter(adapter2);
        people_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                reserve.setPeople(people_spinner.getAdapter().getItem(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Button b_reserve = findViewById(R.id.b_reserve);
        b_reserve.setOnClickListener(view -> ReserveController.request_reserve(this, getPreference(this, "date"), reserve.getHour(), reserve.getPeople(), getPreference(this, "table")));

        Button b_cancel = findViewById(R.id.b_cancel);
        b_cancel.setOnClickListener(view -> startActivity(new Intent(this, ReserveActivity.class)));
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
