package com.lostacos.Model;

import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.content.Context;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("master")
    private String master;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMaster() { return master; }

    public User(String id, String name, String email, String phone, String birthday, String cpf, String createdAt, String master) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.cpf = cpf;
        this.createdAt = createdAt;
        this.master = master;
    }

    public void SaveOrUpdateUserInformations(Context context){
        if(!id.equals(getPreference(context, "id"))){
            setPreference(context, "id", id);
        }
        if(!name.equals(getPreference(context, "name"))){
            setPreference(context, "name", name);
        }
        if(!email.equals(getPreference(context, "email"))){
            setPreference(context, "email", email);
        }
        if(!phone.equals(getPreference(context, "phone"))){
            setPreference(context, "phone", phone);
        }
        if(!birthday.equals(getPreference(context, "birthday"))){
            setPreference(context, "birthday", birthday);
        }
        if(!cpf.equals(getPreference(context, "cpf"))){
            setPreference(context, "cpf", cpf);
        }
        if(!createdAt.equals(getPreference(context, "createdAt"))){
            setPreference(context, "createdAt", createdAt);
        }
        if(!master.equals(getPreference(context, "master"))){
            setPreference(context, "master", master);
        }
    }
}