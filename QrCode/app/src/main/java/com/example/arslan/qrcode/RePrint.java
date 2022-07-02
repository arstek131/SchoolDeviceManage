package com.example.arslan.qrcode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RePrint extends AppCompatActivity
{
    ConnectionDetector cd;
    AlertDialog.Builder builder;
    private static final String URL_DATA = "http://appqr.altervista.org/QrReprint.php";
    private Button bReprint;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_print);

        cd = new ConnectionDetector(this);
        final EditText etNumInventario = (EditText) findViewById(R.id.NumInventarioRePrint);
         bReprint = (Button) findViewById(R.id.btnSend);

        bReprint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cd.isNetworkAvailable())
                {
                    final String NumInventario = etNumInventario.getText().toString();

                    //bReprint.setEnabled(false);

                    if(NumInventario.equals(""))
                    {
                        AlertDialog.Builder b = new AlertDialog.Builder(RePrint.this);
                        b.setCancelable(false);
                        b.setTitle("Errore");
                        b.setMessage("Non puoi lasciare il campo vuoto!");
                        b.setPositiveButton("OK",null);
                        b.create().show();
                    }
                    else
                    {
                        final ProgressDialog progressDialog = new ProgressDialog(RePrint.this);
                        progressDialog.setMessage("Carimento dati...");
                        progressDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                URL_DATA,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String s)
                                    {
                                        progressDialog.dismiss();
                                        try {
                                            JSONObject resp = new JSONObject(s);
                                            String code = resp.getString("code");
                                            String message = resp.getString("message");
                                            if(code.equals("reg_failed"))
                                            {
                                                AlertDialog.Builder b = new AlertDialog.Builder(RePrint.this);
                                                b.setCancelable(false);
                                                b.setTitle("Risposta del server");
                                                b.setMessage(message);
                                                b.setPositiveButton("OK",null);
                                                b.create().show();
                                            }
                                            else
                                            {
                                                Intent intent = new Intent(getApplicationContext(), QrPrint.class);
                                                intent.putExtra("tmp", message);
                                                startActivity(intent);

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("NumInventario", NumInventario);
                                return params;
                            }
                        };

                        RegisterRequest.getmInstance(RePrint.this).addToRequestque(stringRequest);
                    }


                }

                else
                {
                    Toast.makeText(RePrint.this, "Nessuna connesione ad Internet!",Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RePrint.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
