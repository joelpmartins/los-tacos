package com.lostacos.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.lostacos.R;
import com.lostacos.controller.LanguageController;
import com.lostacos.controller.LoginController;


public class SplashScreen extends AppCompatActivity {

    LoginController loginController = new LoginController();
    LanguageController languageController = new LanguageController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        languageController.getLocale(this);

        new Handler().postDelayed(() -> {
            if(!loginController.checkToken(this)){
                startActivity(new Intent(this, LoginActivity.class));
            }else{
                startActivity(new Intent(this, LoadingScreen.class));
            }
            finish();
        }, 3000);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
