package com.lostacos.controller;

import static com.lostacos.util.Strings.getStringByName;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lostacos.Model.User;
import com.lostacos.R;
import com.lostacos.util.Constants;
import com.lostacos.view.HomeActivity;
import com.lostacos.view.LoadingScreen;
import com.lostacos.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    public boolean checkToken(Context context){
        String token = getPreference(context, "token");
        if(token != null){
            validateToken(context, token);
            return true;
        }else{
            return false;
        }
    }
    private void validateToken(Context context, String token){
       String url = Constants.URL_BASE + Constants.URL_LOGIN + Constants.URL_CHECKTOKEN;
       try {
            Gson gson = new Gson();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", token);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        User user = gson.fromJson(response.toString(), User.class);
                        user.SaveOrUpdateUserInformations(context);
                        context.startActivity(new Intent(context, HomeActivity.class));
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
                            if(message.equals("Acesso negado! Token inválido.")) {
                                deletePreference(context, "token");
                                context.startActivity(new Intent(context, LoginActivity.class));
                                Toast.makeText(context.getApplicationContext(), "Você foi desconectado(a)! Sua conta foi acessada em outro dispositivo.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context.getApplicationContext(), "Você foi desconectado(a)! Sua sessão expirou.", Toast.LENGTH_LONG).show();
                                deletePreference(context, "token");
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                public Map getHeaders() throws AuthFailureError
                {
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + getPreference(context, "token"));
                    return headers;
                }};
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
       }catch (JSONException ignored){}
    }

    public void login(Context context, String email, String password){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        EditText email_input = ((Activity) context).findViewById(R.id.email_input);
        EditText password_input = ((Activity)context).findViewById(R.id.password_input);

        if(email.length() == 0 ) {
            email_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(!email.matches(emailPattern)){
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_invalido"), Toast.LENGTH_SHORT).show();
        }else if(password.length() == 0){
            password_input.setError(getStringByName(context, "campo_obrigatorio"), null);
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_obrigatoria"), Toast.LENGTH_SHORT).show();
        }else if(password.length() < 8) {
            password_input.setError(getStringByName(context, "senha_caracteres"), null);
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_caracteres"), Toast.LENGTH_SHORT).show();
        } else{
            auth(context, email, password);
        }
    }

    private void auth(Context context, String email, String password){
        String url = Constants.URL_BASE + Constants.URL_LOGIN;
        LoadingScreen.StartLoading(context);
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            Map<String, String> params = new HashMap<>();
            params.put("key", getPreference(context, "token"));
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response.toString());
                            String token = jsonObj.getString("token");
                            setPreference(context, "token", token);
                            validateToken(context, token);
                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
                        }
                        LoadingScreen.StopLoading();
                    },
                    error -> {
                        JSONObject jsonObj;
                        String message;
                        NetworkResponse response = error.networkResponse;
                        try {
                            message = new String(response.data, StandardCharsets.UTF_8);
                            jsonObj = new JSONObject(message);
                            message = jsonObj.getString("message");

                            if(message.equals("Esse endereço de e-mail não está cadastrado")) {
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_nao_cadastrado"), Toast.LENGTH_SHORT).show();
                            }else if(message.equals("Senha incorreta!")) {
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_incorreta"), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
                        }
                        LoadingScreen.StopLoading();
                    });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        }catch (JSONException ignored){ }
    }
}
