package com.example.arslan.qrcode;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arslan on 26/05/2017.
 */

public class RetrieveObjectDataRequest extends StringRequest {
    private static final String FILE = "http://appqr.altervista.org/QrQuery.php";
    private Map<String, String> params;

    public RetrieveObjectDataRequest(String qrCode, String Username, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, FILE, listener, errorListener);
        params = new HashMap<>(2);
        params.put("qrcode", qrCode);
        params.put("Username", Username);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
