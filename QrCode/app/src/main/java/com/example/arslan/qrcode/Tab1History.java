package com.example.arslan.qrcode;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tab1History extends Fragment
{
    SwipeRefreshLayout swipeToRef;
    private RecyclerView recyclerView;
    private CespiteAdapter adapter;
    UserSessionManager session;
    ConnectionDetector cd;
    View rootView;

    private static final String URL_DATA = "http://appqr.altervista.org/QrHistory.php";

    private List<CespiteOgg> cespiteOggList;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        cd = new ConnectionDetector(getActivity());
        rootView = inflater.inflate(R.layout.tab1history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cespiteOggList = new ArrayList<>();

        // SwipeRefreshLayout

        swipeToRef = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeToRef.setColorSchemeResources(
                R.color.colorAccent
        );
       /* mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent
                );
*/
        loadRecyclerViewData();

        swipeToRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeToRef.setRefreshing(true);

                cespiteOggList = new ArrayList<CespiteOgg>();

                if(cd.isNetworkAvailable()){
                    loadRecyclerViewData();
                } else {
                    swipeToRef.setRefreshing(false);
                    Toast.makeText(getContext(), "Nessuna connessione", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }

    private void loadRecyclerViewData()
    {
        cespiteOggList.clear();
        //cespiteOggList = new ArrayList<>();
        // Showing refresh animation before making http call
        //swipeToRef.setRefreshing(true);


        // Session class instance
        session = new UserSessionManager(getActivity());
        //get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get name
        String name = user.get(UserSessionManager.KEY_NAME);
        // get username
        final String usernameUtente = user.get(UserSessionManager.KEY_USERNAME);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        TextView noElem = (TextView) rootView.findViewById(R.id.noElem);

                        if(s.equals("")){
                            noElem.setVisibility(View.VISIBLE);
                            cespiteOggList.clear();

                            if(adapter != null){
                                adapter.clearCerpiteList();
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            noElem.setVisibility(View.GONE);

                            try {

                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray array;
                                array = jsonObject.getJSONArray("dates");

                                for(int i=0; i<array.length(); i++)
                                {
                                    JSONObject o = array.getJSONObject(i);
                                    CespiteOgg item = new CespiteOgg(
                                            o.getString("CodNumInventario"),
                                            o.getString("Nome"),
                                            o.getString("DtCatalogazione"),
                                            o.getString("CodIdA"),
                                            o.getString("username")
                                    );
                                    cespiteOggList.add(item);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new CespiteAdapter(cespiteOggList, getActivity());
                            recyclerView.setAdapter(adapter);
                        }

                        // Stopping swipe refresh
                        swipeToRef.setRefreshing(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Stopping swipe refresh
                        swipeToRef.setRefreshing(false);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", usernameUtente);
                return params;
            }
        };

        RegisterRequest.getmInstance(getActivity()).addToRequestque(stringRequest);


    }


}
