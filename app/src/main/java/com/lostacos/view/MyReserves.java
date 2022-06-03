package com.lostacos.view;

import static com.lostacos.util.DataFormat.setFormatDateInString;
import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.getPreference;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lostacos.R;
import com.lostacos.util.DataFormat;
import com.lostacos.util.adapter.ReservesListAdapter;
import com.lostacos.util.network.NetworkChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyReserves extends AppCompatActivity {
    final String url = "https://los-tacos.herokuapp.com/reserve/reservations";

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TextView title;

    static ArrayList<com.lostacos.Model.MyReserves> ReservesList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        title = (TextView) findViewById(R.id.textView1);
        title.setText(getStringByName(this, "reservas"));

        ListView listview = (ListView) findViewById(R.id.listView);

        for(int i=0; i<=6; i++){
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", getPreference(this, "id"));
                jsonBody.put("date", DataFormat.getDay(i));
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        response -> {
                            JSONObject jsonObj;
                            try {
                                jsonObj = new JSONObject(response.toString());
                                getReserves(this, jsonObj.getString("id"), jsonObj.getString("date"), jsonObj.getString("hour"), jsonObj.getString("people"), listview);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {});
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(jsonObjectRequest);
            }catch (JSONException ignored){}
        }

        ImageView b_back = findViewById(R.id.b_back);

        b_back.setOnClickListener(view -> {
            ReservesList.clear();
            MyReserves.this.startActivity(new Intent(MyReserves.this, ReserveActivity.class));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void getReserves(Context context, String id, String date, String hour, String people, ListView listview) {
        com.lostacos.Model.MyReserves myReserves = new com.lostacos.Model.MyReserves(id, setFormatDateInString(date), hour, people);
        ReservesList.add(myReserves);
        ReservesListAdapter adapter = new ReservesListAdapter(context, R.layout.adapter_view_layout, ReservesList);
        listview.setAdapter(adapter);
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
