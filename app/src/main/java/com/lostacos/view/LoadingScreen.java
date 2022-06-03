package com.lostacos.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lostacos.R;

public class LoadingScreen extends AppCompatActivity {
    static AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loading_screen);
        StartLoading(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        dialog.dismiss();
        super.onStop();
    }

    public static void StartLoading(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout_dialog = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        builder.setView(layout_dialog);
        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
    public static void StopLoading(){
        dialog.dismiss();
    }
}
