package com.lostacos.view;

import static com.lostacos.util.Strings.getStringByName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.lostacos.Model.Food;
import com.lostacos.R;
import com.lostacos.util.adapter.FoodListAdapter;
import com.lostacos.util.network.NetworkChangeListener;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ListView listview = (ListView) findViewById(R.id.listView);

        Food taco = new Food("Taco", getStringByName(this, "taco"),"28,00");
        Food guacamole = new Food("Guacamole", getStringByName(this, "guacamole"),"28,50");
        Food tortillas = new Food("Tortillas", getStringByName(this, "tortillas"),"25,00");
        Food chilli = new Food("Chilli", getStringByName(this, "chilli"),"40,00");
        Food burrito = new Food("Burrito", getStringByName(this, "burrito"),"35,00");
        Food nacho = new Food("Nacho", getStringByName(this, "nacho"),"22,50");

        ArrayList<Food> MenuList = new ArrayList<>();
        MenuList.add(taco);
        MenuList.add(guacamole);
        MenuList.add(tortillas);
        MenuList.add(chilli);
        MenuList.add(burrito);
        MenuList.add(nacho);

        FoodListAdapter adapter = new FoodListAdapter(this, R.layout.adapter_view_layout, MenuList);
        listview.setAdapter(adapter);

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