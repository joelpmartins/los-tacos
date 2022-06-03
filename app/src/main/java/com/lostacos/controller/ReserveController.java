package com.lostacos.controller;

import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.getPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lostacos.Model.Reserve;
import com.lostacos.Model.Tables;
import com.lostacos.R;
import com.lostacos.util.Constants;
import com.lostacos.view.HomeActivity;
import com.lostacos.view.LoadingScreen;
import com.lostacos.view.ReserveActivity;
import com.lostacos.view.ReserveProcess;

import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReserveController {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void LoadReserves(Context context){
        Spinner spinner;

        Tables tables = new Tables();
        Reserve reserve = new Reserve();

        spinner = ((Activity)context).findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();
        for(int i=0; i<=6; i++){
            list.add(getDay(i));
        }

        checkTables(context, list.get(0));
        reserve.setDate(context, list.get(0));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                LoadingScreen.StartLoading(context);
                reserve.setDate(context, spinner.getAdapter().getItem(position).toString());
                checkTables(context, spinner.getAdapter().getItem(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        CardView b_table1 = ((Activity) context).findViewById(R.id.table1);
        b_table1.setOnClickListener(view -> {
            if(!tables.getTable(context, 1)) {
                reserve.setTable(context,1);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table2 = ((Activity) context).findViewById(R.id.table2);
        b_table2.setOnClickListener(view -> {
            if(!tables.getTable(context,2)) {
                reserve.setTable(context,2);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table3 = ((Activity) context).findViewById(R.id.table3);
        b_table3.setOnClickListener(view -> {
            if(!tables.getTable(context,3)) {
                reserve.setTable(context,3);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table4 = ((Activity) context).findViewById(R.id.table4);
        b_table4.setOnClickListener(view -> {
            if(!tables.getTable(context, 4)) {
                reserve.setTable(context,4);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table5 = ((Activity) context).findViewById(R.id.table5);
        b_table5.setOnClickListener(view -> {
            if(!tables.getTable(context, 5)) {
                reserve.setTable(context,5);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table6 = ((Activity) context).findViewById(R.id.table6);
        b_table6.setOnClickListener(view -> {
            if(!tables.getTable(context, 6)) {
                reserve.setTable(context,6);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table7 = ((Activity) context).findViewById(R.id.table7);
        b_table7.setOnClickListener(view -> {
            if(!tables.getTable(context, 7)) {
                reserve.setTable(context,7);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table8 = ((Activity) context).findViewById(R.id.table8);
        b_table8.setOnClickListener(view -> {
            if(!tables.getTable(context, 8)) {
                reserve.setTable(context,8);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
        CardView b_table9 = ((Activity) context).findViewById(R.id.table9);
        b_table9.setOnClickListener(view -> {
            if(!tables.getTable(context, 9)) {
                reserve.setTable(context,9);
                context.startActivity(new Intent(context, ReserveProcess.class));
            }else{
                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void request_reserve(Context context, String date, String hour, String people, String table){
        LoadingScreen.StartLoading(context);
        final String url = Constants.URL_BASE + Constants.URL_RESERVE;
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", getPreference(context, "id"));
            jsonBody.put("date", date);
            jsonBody.put("hour", hour);
            jsonBody.put("amount", people);
            jsonBody.put("table", table);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response.toString());
                            String message = jsonObj.getString("message");
                            if(message.equals("Reserva realizada com sucesso!")){
                                LoadingScreen.StopLoading();
                                context.startActivity(new Intent(context, HomeActivity.class));
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "reservado_sucesso"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        JSONObject jsonObj;
                        String message;
                        NetworkResponse response = error.networkResponse;
                        try {
                            message = new String(response.data, StandardCharsets.UTF_8);
                            jsonObj = new JSONObject(message);
                            message = jsonObj.getString("message");
                            LoadingScreen.StopLoading();
                            if(message.equals("Você só pode reservar uma mesa por dia!")){
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesas_permitidas"), Toast.LENGTH_LONG).show();
                            }else if(message.equals("A mesa já foi reservada!")){
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "mesa_reservada"), Toast.LENGTH_LONG).show();
                            }
                            context.startActivity(new Intent(context, ReserveActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        }catch (JSONException ignored){}
    }

    private static void checkTables(Context context, String date){
        final String url = Constants.URL_BASE + Constants.URL_RESERVE + Constants.URL_CHECKTABLE;
        for(int i=1; i<=9; i++){
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("table", i);
                jsonBody.put("date", date);
                String tableid = "table" + i;
                int resID = context.getResources().getIdentifier(tableid, "id", context.getPackageName());
                CardView cardview = ((Activity) context).findViewById(resID);
                int j = i;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        response -> {
                            JSONObject jsonObj;
                            Tables tables = new Tables();
                            try {
                                jsonObj = new JSONObject(response.toString());
                                String message = jsonObj.getString("message");
                                if(message.equals("Mesa não reservada!")){
                                    tables.setTable(context, j, false);
                                    cardview.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                                }else if(message.equals("Mesa reservada!")){
                                    tables.setTable(context, j, true);
                                    cardview.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                                }
                                if(j == 9){
                                    LoadingScreen.StopLoading();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            JSONObject jsonObj;
                            String message;
                            NetworkResponse response = error.networkResponse;
                            try {
                                message = new String(response.data, StandardCharsets.UTF_8);
                                jsonObj = new JSONObject(message);
                                message = jsonObj.getString("message");
                                Log.e(message, message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(jsonObjectRequest);
            }catch (JSONException ignored){}
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String getDay(Integer a){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, a);
        calendar.add(Calendar.HOUR, 3); // 3 horas para compensar a diferença de horário do Heroku para teste
        Date day = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(day);
    }
}
