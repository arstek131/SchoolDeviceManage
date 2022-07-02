package com.example.arslan.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileSetting extends AppCompatActivity {

    private Button btn_log_outBtn;
    private Button btn_changePw;
    private TextView prp_userName;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        btn_log_outBtn = (Button) findViewById(R.id.btnout);
        btn_changePw = (Button) findViewById(R.id.btnChangepw);
        prp_userName = (TextView) findViewById(R.id.ProperUserName);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        //get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get name
        final String name = user.get(UserSessionManager.KEY_USERNAME);

        prp_userName.setText(name);

        btn_log_outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_log_outBtn.setEnabled(false);
                session.logoutUser();
                finishAffinity();
            }
        });


        btn_changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetting.this, ChangePassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileSetting.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
