package com.example.arslan.qrcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;
    //Check internet connection
    ConnectionDetector cd;

    SecurePW pw;

    String richiesta;
    String success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
             richiesta = bundle.getString("tmp");
             success = bundle.getString("success");
             etPassword.requestFocus();
        }



        // User Session Manager

        cd = new ConnectionDetector(this);
        session = new UserSessionManager(getApplicationContext());
        pw = new SecurePW(this);




        etUsername.setText(richiesta);
        /*
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();
                */


            registerLink.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);

                }

            });



        bLogin.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {

                    if(cd.isNetworkAvailable())
                    {
                        final String username= etUsername.getText().toString();
                        final String password= etPassword.getText().toString();

                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("Caricamento");
                        progressDialog.setMessage("Attendere prego...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        bLogin.setEnabled(false);


                        Response.Listener<String> responseListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");


                                    if(success)
                                    {
                                        String name = jsonResponse.getString("name");

                                        Intent intent = new Intent(LoginActivity.this, Main.class);
                                        session.createUserLoginSession(name,username);
                                        LoginActivity.this.startActivity(intent);
                                        progressDialog.cancel();

                                        finishAffinity();
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setCancelable(false);
                                        builder.setTitle("Risposta del server");
                                        builder.setMessage("Login fallito!")
                                                .setNegativeButton("Riprova",null)
                                                .create()
                                                .show();
                                        progressDialog.cancel();
                                        etPassword.setText("");
                                        bLogin.setEnabled(true);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        LoginRequest loginRequest = null;
                        try {
                            loginRequest = new LoginRequest(username,pw.encrypt(password),responseListener);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Nessuna connesione ad Internet!",Toast.LENGTH_SHORT).show();
                    }
                    }


                });
    }
    }
