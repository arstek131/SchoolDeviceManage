package com.example.arslan.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

//https://www.shinobicontrols.com/blog/bitesize-android-kitkat-week-2-print-framework
public class QrPrint extends AppCompatActivity {

    ImageView imageQr;
    private Button btnPrint;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_print);

        cd = new ConnectionDetector(this);
        btnPrint = (Button) findViewById(R.id.btnPrint);
        imageQr= (ImageView) findViewById(R.id.imageQr);

        Bundle bundle = getIntent().getExtras();
        final String richiesta = bundle.getString("tmp");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(richiesta, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageQr.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isNetworkAvailable())
                {
                    PrintHelper printHelper = new PrintHelper(QrPrint.this);
                    printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                    // Get the image
                    Bitmap image = getImage();
                    if (image != null) {
                        // Send it to the print helper
                        printHelper.printBitmap("PrintShop", image);
                    }
                }
                else
                {
                    Toast.makeText(QrPrint.this, "Nessuna connesione ad Internet!",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public Bitmap getImage()
    {
        ImageView imageView = (ImageView) findViewById(R.id.imageQr);
        Bitmap image = null;
        // Get the image
        if ((imageView.getDrawable()) != null) {
            // Send it to the print helper
            image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
        return image;
    }
}
