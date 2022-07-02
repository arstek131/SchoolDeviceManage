package com.example.arslan.qrcode;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText currentPW;
    EditText newPw;
    EditText confirmnewPw;
    Button btn_send;
    ConnectionDetector cd;
    AlertDialog.Builder builder;
    UserSessionManager session;
    SecurePW pw;
    String reg_url = "http://appqr.altervista.org/ChangePassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPW = (EditText) findViewById(R.id.etCurrentPassword);
        newPw = (EditText) findViewById(R.id.newPassword);
        confirmnewPw = (EditText) findViewById(R.id.confirmNewPassword);
        btn_send = (Button) findViewById(R.id.btnSendValue);

        builder = new AlertDialog.Builder(ChangePassword.this);
        cd = new ConnectionDetector(getApplicationContext());
        pw = new SecurePW(this);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        //get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get name
        final String Username = user.get(UserSessionManager.KEY_USERNAME);

        btn_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cd.isNetworkAvailable())
                {
                    final String etcurrentPW = currentPW.getText().toString();
                    final String etnewPw = newPw.getText().toString();
                    final String etconfirmnewPw = confirmnewPw.getText().toString();

                    if(etcurrentPW.equals("") || etnewPw.equals("") || etconfirmnewPw.equals(""))
                    {
                        builder.setCancelable(false);
                        builder.setTitle("Errore");
                        builder.setMessage("Perfavore compila tutti i campi");
                        builder.setPositiveButton("OK",null);
                        builder.create().show();
                    }
                    else if(!(etnewPw.equals(etconfirmnewPw)))
                    {
                        builder.setTitle("Errore");
                        builder.setMessage("Le password non corrispondono!");
                        displayAlert("input_error");
                    }
                    else if (etnewPw.length()<5)
                    {
                        newPw.setError("Almeno 5 caratteri!");
                        newPw.setText("");
                        confirmnewPw.setText("");
                    }
                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        try {
                                            JSONObject resp = new JSONObject(response);
                                            String code = resp.getString("code");
                                            String message = resp.getString("message");
                                            builder.setTitle("Risposta del server");
                                            builder.setMessage(message);
                                            displayAlert(code);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Username", Username);
                                try {
                                    params.put("Password", pw.encrypt(etnewPw));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    params.put("OldPassword", pw.encrypt(etcurrentPW));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return params;
                            }
                        };
                        RegisterRequest.getmInstance(ChangePassword.this).addToRequestque(stringRequest);
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nessuna connesione ad Internet!",Toast.LENGTH_SHORT).show();
                }
            }

            public void displayAlert(final String code)
            {
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (code.equals("input_error")) {
                            newPw.setText("");
                            confirmnewPw.setText("");
                        } else {
                            if (code.equals("reg_success")) {
                                finish();
                            } else if (code.equals("reg_failed")) {
                                currentPW.setText("");
                                newPw.setText("");
                                confirmnewPw.setText("");
                            }
                        }
                    }

                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


}
