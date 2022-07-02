package com.example.arslan.qrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Tab3Add extends Fragment {

    String reg_url = "http://appqr.altervista.org/QrAdd.php";
    ConnectionDetector cd;

    private String QrCode;
    private EditText NumInventario;
    private EditText NomeCespite;
    private String Categoria;
    private String TipoAula;
    private EditText Aula;
    private EditText Descrizione;
    private EditText Foto;

    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();

    UserSessionManager session;

    private void clearViews()
    {
        NumInventario.setText("");
        NomeCespite.setText("");
        Aula.setText("");
        Descrizione.setText("");
        Foto.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        clearViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /*getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
*/

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View rootView = inflater.inflate(R.layout.tab3add, container, false);

        // Session class instance
        session = new UserSessionManager(getContext());
        //get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get name
        String name = user.get(UserSessionManager.KEY_NAME);
        // get username
        final String username = user.get(UserSessionManager.KEY_USERNAME);

        cd = new ConnectionDetector(getContext());

         NumInventario = (EditText) rootView.findViewById(R.id.NumInventario);
         NomeCespite = (EditText) rootView.findViewById(R.id.NomeCespite);
         //Categoria= (EditText) rootView.findViewById(R.id.Categoria);
         Aula= (EditText) rootView.findViewById(R.id.NumAula);
         Descrizione = (EditText) rootView.findViewById(R.id.Descrizione);
         Foto = (EditText) rootView.findViewById(R.id.Foto);
        final Button btnSend = (Button)  rootView.findViewById(R.id.btnSend);

        //Spinner for categories
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_names, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_add);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Categoria = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selezionato",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //Spinner for rooms
        Spinner sp = (Spinner) rootView.findViewById(R.id.spinnerRoom);
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getContext(), R.array.room_names, android.R.layout.simple_spinner_dropdown_item);
        adap.setDropDownViewResource(R.layout.spinner_item_add);
        sp.setAdapter(adap);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                TipoAula = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selezionato",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    if(cd.isNetworkAvailable())
                    {

                        final String etNumInventario = NumInventario.getText().toString();
                        final String etNomeCespite = NomeCespite.getText().toString();
                        //final String etCategoria = Categoria.getText().toString();
                        final String etAula = Aula.getText().toString();
                        final String etDescrizione = Descrizione.getText().toString();
                        final String etFoto = Foto.getText().toString();
                        QrCode =etNumInventario+ts;

                        if(etNumInventario.equals("")|| etNomeCespite.equals("")  /*etCategoria.equals("")*/|| etAula.equals("") )
                        {
                            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                            b.setCancelable(false);
                            b.setTitle("Errore");
                            b.setMessage("Per favore compila i campi obbligatori!");
                            b.setPositiveButton("OK",null);
                            b.create().show();
                        }

                        else
                        {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject resp = new JSONObject(response);
                                                String code = resp.getString("code");
                                                String message = resp.getString("message");
                                                displayAlert(code, message);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("NumInventario", etNumInventario);
                                    params.put("NomeCespite", etNomeCespite);
                                    params.put("Descrizione", etDescrizione);
                                    params.put("Foto", etFoto);
                                    params.put("QrCode", QrCode);
                                    params.put("Categoria",Categoria);
                                    params.put("Aula",etAula);
                                    params.put("TipoAula",TipoAula);
                                    params.put("Username",username);
                                    return params;
                                }
                            };
                            RegisterRequest.getmInstance(getContext()).addToRequestque(stringRequest);


                        }

                    }
                    else
                    {
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setCancelable(false);
                        b.setTitle("Errore");
                        b.setMessage("Per favore controlla la tua connessione ad internet!");
                        //b.setMessage(username);
                        b.setPositiveButton("OK",null);
                        b.show();
                    }

                    /*
                else
                {
                    b.setTitle("Errore");
                    b.setMessage("Il cespite risulta essere già registrato!");
                    b.setPositiveButton("OK",null);
                    b.show();
                    NumInventario.requestFocus();
                }
                */

            }

            private void displayAlert(final String code, String message)
            {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setCancelable(false);
                b.setTitle("Risposta del server");
                b.setMessage(message);
                b.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(code.equals("reg_failed"))
                        {
                            NumInventario.requestFocus();
                        }
                        else if (code.equals("reg_success"))
                        {

                            Intent intent = new Intent(getActivity(), QrPrint.class);
                            intent.putExtra("tmp", QrCode);
                            startActivity(intent);

                        }
                    }
                });
                b.create().show();
            }
        });

        return rootView;

    }
}
