package com.example.arslan.qrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ShowData extends AppCompatActivity
{
    UserSessionManager session;
    TextView Nome, Descrizione, Numinventario, Foto, Dtcatalogazione, NumAula, TipoAula, Categoria, DescriCategoria, NomeUser, RuoloUser ;
    TextView etNome, etDescrizione, etNuminventario, etFoto, etDtcatalogazione, etNumAula, etTipoAula, etCategoria, etDescriCategoria, etNomeUser, etRuoloUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        //get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get name
        String name = user.get(UserSessionManager.KEY_NAME);
        // get username
        final String usernameUtente = user.get(UserSessionManager.KEY_USERNAME);

        Bundle bundle = getIntent().getExtras();
        final String richiesta = bundle.getString("variabile");

        Nome = (TextView) findViewById(R.id.Nome);
        Nome.setVisibility(TextView.INVISIBLE);
        etNome = (TextView) findViewById(R.id.textView4) ;
        etNome.setVisibility(TextView.INVISIBLE);

        Descrizione = (TextView) findViewById(R.id.Descrizione);
        Descrizione.setVisibility(TextView.INVISIBLE);
        etDescrizione = (TextView) findViewById(R.id.textView7);
        etDescrizione.setVisibility(TextView.INVISIBLE);

        Numinventario = (TextView) findViewById(R.id.Numinventario);
        Numinventario.setVisibility(TextView.INVISIBLE);
        etNuminventario = (TextView) findViewById(R.id.textView3);
        etNuminventario.setVisibility(TextView.INVISIBLE);

        Foto = (TextView) findViewById(R.id.Foto);
        Foto.setVisibility(TextView.INVISIBLE);
        etFoto = (TextView) findViewById(R.id.textView5);
        etFoto.setVisibility(TextView.INVISIBLE);

        Dtcatalogazione = (TextView) findViewById(R.id.Datacat);
        Dtcatalogazione.setVisibility(TextView.INVISIBLE);
        etDtcatalogazione = (TextView) findViewById(R.id.textView6);
        etDtcatalogazione.setVisibility(TextView.INVISIBLE);

        NumAula = (TextView) findViewById(R.id.NumeroAula);
        NumAula.setVisibility(TextView.INVISIBLE);
        etNumAula = (TextView) findViewById(R.id.textView9);
        etNumAula.setVisibility(TextView.INVISIBLE);

        TipoAula= (TextView) findViewById(R.id.TipologiaAula);
        TipoAula.setVisibility(TextView.INVISIBLE);
        etTipoAula = (TextView) findViewById(R.id.textView25);
        etTipoAula.setVisibility(TextView.INVISIBLE);

        Categoria= (TextView) findViewById(R.id.Categoria);
        Categoria.setVisibility(TextView.INVISIBLE);
        etCategoria = (TextView) findViewById(R.id.textView11);
        etCategoria.setVisibility(TextView.INVISIBLE);

        DescriCategoria= (TextView) findViewById(R.id.Descrizione_categoria);
        DescriCategoria.setVisibility(TextView.INVISIBLE);
        etDescriCategoria = (TextView) findViewById(R.id.textView26);
        etDescriCategoria.setVisibility(TextView.INVISIBLE);

        NomeUser= (TextView) findViewById(R.id.Nome_user_registratore);
        NomeUser.setVisibility(TextView.INVISIBLE);
        etNomeUser = (TextView) findViewById(R.id.textView13);
        etNomeUser.setVisibility(TextView.INVISIBLE);

        RuoloUser=(TextView) findViewById(R.id.Ruolo_user_registratore);
        RuoloUser.setVisibility(TextView.INVISIBLE);
        etRuoloUser = (TextView) findViewById(R.id.textView27);
        etRuoloUser.setVisibility(TextView.INVISIBLE);


        final ProgressDialog progressDialog = new ProgressDialog(ShowData.this);
        progressDialog.setTitle("Caricamente");
        progressDialog.setMessage("Attendere prego...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);

                    switch (obj.getString("error_code"))
                    {
                        case "0": // nessun errore
                            JSONObject data = obj.getJSONObject("data");
                            etNome.setVisibility(TextView.VISIBLE);
                            Nome.setVisibility(TextView.VISIBLE);
                            Nome.setText(data.getString("Nome"));
                            etDescrizione.setVisibility(TextView.VISIBLE);
                            Descrizione.setVisibility(TextView.VISIBLE);
                            Descrizione.setText(data.getString("Descrizione"));
                            etNuminventario.setVisibility(TextView.VISIBLE);
                            Numinventario.setVisibility(TextView.VISIBLE);
                            Numinventario.setText(data.getString("NumInventario"));
                            etFoto.setVisibility(TextView.VISIBLE);
                            Foto.setVisibility(TextView.VISIBLE);
                            Foto.setText(data.getString("Foto"));
                            etDtcatalogazione.setVisibility(TextView.VISIBLE);
                            Dtcatalogazione.setVisibility(TextView.VISIBLE);
                            Dtcatalogazione.setText(data.getString("DtCatalogazione"));
                            etNumAula.setVisibility(TextView.VISIBLE);
                            NumAula.setVisibility(TextView.VISIBLE);
                            NumAula.setText(data.getString("CodIdA"));
                            etTipoAula.setVisibility(TextView.VISIBLE);
                            TipoAula.setVisibility(TextView.VISIBLE);
                            TipoAula.setText(data.getString("Tipo"));
                            etCategoria.setVisibility(TextView.VISIBLE);
                            Categoria.setVisibility(TextView.VISIBLE);
                            Categoria.setText(data.getString("A_H"));
                            etDescriCategoria.setVisibility(TextView.VISIBLE);
                            DescriCategoria.setVisibility(TextView.VISIBLE);
                            DescriCategoria.setText(data.getString("Descrizione_Categoria"));
                            etNomeUser.setVisibility(TextView.VISIBLE);
                            NomeUser.setVisibility(TextView.VISIBLE);
                            NomeUser.setText(data.getString("name"));
                            etRuoloUser.setVisibility(TextView.VISIBLE);
                            RuoloUser.setVisibility(TextView.VISIBLE);
                            RuoloUser.setText(data.getString("Ruolo"));
                            break;
                        case "1": //  cespite non esiste nel database
                            AlertDialog.Builder b = new AlertDialog.Builder(ShowData.this);
                            b.setCancelable(false);
                            b.setTitle("Errore");
                            b.setMessage("Il cespite non è registrato nel database.");
                            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ShowData.this, Main.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            b.create().show();
                            break;
                        default: // default case
                            AlertDialog.Builder c = new AlertDialog.Builder(ShowData.this);
                            c.setMessage("default case");
                            c.create().show();
                            break;
                    }
                    progressDialog.cancel();;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                AlertDialog.Builder b = new AlertDialog.Builder(ShowData.this);
                b.setCancelable(false);
                b.setTitle("Errore");
                b.setMessage("Errore di connessione.\nPer favore riesegui la scansione");
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ShowData.this, Main.class);
                        startActivity(intent);
                        finish();
                    }
                });
                b.create().show();
            }
        };

        RetrieveObjectDataRequest request = new RetrieveObjectDataRequest(richiesta, usernameUtente, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowData.this, Main.class);
        startActivity(intent);
        finish();

    }
}
