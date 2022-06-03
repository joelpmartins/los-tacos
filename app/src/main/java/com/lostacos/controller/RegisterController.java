package com.lostacos.controller;

import static com.lostacos.util.Strings.getStringByName;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lostacos.R;
import com.lostacos.util.Constants;
import com.lostacos.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RegisterController {
    public void register(Context context, String name, String phone, String email, String cpf, String birthday, String password, String confirmPassword){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        EditText name_input = (EditText) ((Activity) context).findViewById(R.id.name_input);
        EditText phone_input = (EditText) ((Activity) context).findViewById(R.id.phone_input);
        EditText email_input = (EditText) ((Activity) context).findViewById(R.id.email_input);
        EditText cpf_input = (EditText) ((Activity) context).findViewById(R.id.cpf_input);
        EditText birthday_input = (EditText) ((Activity) context).findViewById(R.id.birthday_input);
        EditText password_input = (EditText) ((Activity)context).findViewById(R.id.password_input);

        if(name.length() == 0 ) {
            name_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "nome_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(phone.length() == 0 ) {
            phone_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "telefone_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(email.length() == 0 ) {
            email_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(cpf.length() == 0 ) {
            cpf_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "cpf_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(birthday.length() == 0 ) {
            birthday_input.setError(getStringByName(context, "campo_obrigatorio"));
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "aniversario_obrigatorio"), Toast.LENGTH_SHORT).show();
        }else if(!email.matches(emailPattern)){
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_invalido"), Toast.LENGTH_SHORT).show();
        }else if(password.length() == 0){
            password_input.setError(getStringByName(context, "campo_obrigatorio"), null);
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_obrigatoria"), Toast.LENGTH_SHORT).show();
        }else if(password.length() < 8) {
            password_input.setError(getStringByName(context, "senha_caracteres"), null);
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_caracteres"), Toast.LENGTH_SHORT).show();
        }else if(confirmPassword.length() == 0) {
            password_input.setError(getStringByName(context, "campo_obrigatorio"), null);
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_confirme"), Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirmPassword)) {
            Toast.makeText(context.getApplicationContext(), getStringByName(context, "senha_confirmar"), Toast.LENGTH_SHORT).show();
        }else{
            save(context, name, phone, email, cpf, birthday, password, confirmPassword);
        }
    }
    public void save(Context context, String name, String phone, String email, String cpf, String birthday, String password, String confirmPassword){
        String url = Constants.URL_BASE + Constants.URL_REGISTER;
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("phone", phone);
            jsonBody.put("email", email);
            jsonBody.put("cpf", cpf);
            jsonBody.put("birthday", birthday);
            jsonBody.put("password", password);
            jsonBody.put("confirmpassword", confirmPassword);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response.toString());
                            String message = jsonObj.getString("message");
                            if(message.equals("Usuário foi cadastrado com sucesso!")){
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "usuario_cadastrado"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
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

                            if(message.equals("Esse endereço de e-mail já está sendo utilizado!")) {
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "email_cadastrado"), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), getStringByName(context, "problema_conexao_servidor"), Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        }catch (JSONException ignored){ }
    }
}
