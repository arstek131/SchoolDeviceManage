package com.example.arslan.qrcode;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class RegisterRequest
{
    private static RegisterRequest mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    //default constructor
    private RegisterRequest(Context context)
    {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx);
        }
        return requestQueue;
    }

    public static synchronized RegisterRequest getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new RegisterRequest(context);
        }
        return mInstance;
    }

    public void addToRequestque(Request request)
    {
        requestQueue.add(request);
    }

}
