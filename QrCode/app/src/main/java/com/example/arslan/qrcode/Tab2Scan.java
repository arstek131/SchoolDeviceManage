package com.example.arslan.qrcode;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;


public class Tab2Scan extends Fragment {

    ConnectionDetector cd;
    // QREader
    private SurfaceView mySurfaceView;
    private QREader qrEader;
    Context thisContext;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab2scan, container, false);


        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        thisContext = getContext();
        cd = new ConnectionDetector(thisContext);

        mySurfaceView = (SurfaceView) rootView.findViewById(R.id.camera_view);

        // Init QREader
        // ------------
        qrEader = new QREader.Builder(thisContext, mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {

                //qrEader.stop();
                Intent intent = new Intent(getActivity(), ShowData.class);
                intent.putExtra("variabile", data);
                startActivity(intent);
                getActivity().finish();
                //Toast.makeText(thisContext, result.getContents(),Toast.LENGTH_LONG).show();

                Log.d("QREader", "Value : " + data);
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();

        qrEader.start();

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();

        // Init and Start with SurfaceView
        // -------------------------------
        qrEader.initAndStart(mySurfaceView);
        qrEader.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Cleanup in onPause()
        // --------------------
        qrEader.releaseAndCleanup();
    }

}


