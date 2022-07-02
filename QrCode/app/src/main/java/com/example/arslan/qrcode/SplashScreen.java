package com.example.arslan.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity{


    private ConnectionDetector cd;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getApplicationContext());
        session = new UserSessionManager(getApplicationContext());

        Intent takeME = new Intent(this, LoginActivity.class);

        if(cd.isNetworkAvailable())
        {
            if(session.isUserLoggedIn()){
                takeME = new Intent(this, Main.class);
            }
        }

        startActivity(takeME);
        finish();


    }

}
