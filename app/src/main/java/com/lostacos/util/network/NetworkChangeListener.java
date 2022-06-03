package com.lostacos.util.network;

import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.lostacos.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkChecker.isConnected(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.dialog_disconnected, null);
            builder.setView(layout_dialog);

            AppCompatImageView image = layout_dialog.findViewById(R.id.imageView);
            String localeName = getPreference(context, "language");
            switch (localeName){
                case "en":{
                    image.setImageResource(R.drawable.message_disconnected_en);
                    break;
                }
                case "es":{
                    image.setImageResource(R.drawable.message_disconnected_es);
                    break;
                }
                default:{
                    image.setImageResource(R.drawable.message_disconnected_pt);
                    break;
                }
            }

            AppCompatButton btnReconnect = layout_dialog.findViewById(R.id.b_reconnect);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            btnReconnect.setOnClickListener(view -> {
                dialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
