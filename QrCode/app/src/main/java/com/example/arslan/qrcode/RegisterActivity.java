package com.example.arslan.qrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.arslan.qrcode.R.id.etConfPassword;
import static com.example.arslan.qrcode.R.id.etPassword;
import static com.example.arslan.qrcode.R.id.parent;

public class RegisterActivity extends AppCompatActivity{

    //Check internet connection
    ConnectionDetector cd;
    AlertDialog.Builder builder;
    String reg_url = "http://appqr.altervista.org/Register.php";
    String ruolo;
    SecurePW pw;
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final String success= "success";

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfPassword = (EditText) findViewById(R.id.etConfPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        builder = new AlertDialog.Builder(RegisterActivity.this);
        cd = new ConnectionDetector(this);
        pw = new SecurePW(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerRule);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rule_names, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ruolo=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selezionato",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        bRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cd.isNetworkAvailable())
                {
                    final String name = etName.getText().toString();
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();
                    final String confpassowrd = etConfPassword.getText().toString();


                    if (name.equals("") || username.equals("") || password.equals("") || confpassowrd.equals(""))
                    {
                        builder.setCancelable(false);
                        builder.setTitle("Errore");
                        builder.setMessage("Perfavore compila tutti i campi");
                        displayAlert("input_error");
                    }
                    else if (!(password.equals(confpassowrd)))
                    {
                        builder.setCancelable(false);
                        builder.setTitle("Errore");
                        builder.setMessage("Le password non corrispondono!");
                        displayAlert("input_error");

                    }
                    else if (password.length() < 5)
                    {
                        etPassword.setError("Almeno 5 caratteri!");
                        etPassword.setText("");
                        etConfPassword.setText("");
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
                                            builder.setCancelable(false);
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
                                params.put("name", name);
                                params.put("username", username);
                                try {
                                    params.put("password", pw.encrypt(password));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                params.put("ruolo", ruolo);

                                return params;
                            }
                        };
                        RegisterRequest.getmInstance(RegisterActivity.this).addToRequestque(stringRequest);
                    }

                }
                else
                    {
                        Toast.makeText(RegisterActivity.this, "Nessuna connesione ad Internet!", Toast.LENGTH_SHORT).show();
                    }


            }

            public void displayAlert(final String code)
            {
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (code.equals("input_error"))
                        {
                            etPassword.setText("");
                            etConfPassword.setText("");
                        } else
                            {
                            if (code.equals("reg_success"))
                            {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("tmp", etUsername.getText().toString());
                                intent.putExtra("success", success);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (code.equals("reg_failed"))
                            {
                                etName.setText("");
                                etUsername.setText("");
                                etPassword.setText("");
                                etConfPassword.setText("");
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










