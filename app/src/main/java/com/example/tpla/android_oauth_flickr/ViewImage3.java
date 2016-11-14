package com.example.tpla.android_oauth_flickr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ViewImage3 extends Activity {
    private static String filePath;
    Button savePic,uploadFlickr;
    ImageView soloPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image3);

        savePic = (Button) findViewById(R.id.Savebutton);
        soloPic = (ImageView) findViewById(R.id.imageView);
        uploadFlickr = (Button) findViewById(R.id.uploadFlickr);

        Intent callerIntent=getIntent();

        Bundle packageFromCaller= callerIntent.getBundleExtra("datas");
        filePath = packageFromCaller.getString("File path");
        Log.d("get File Path", filePath);

        Bitmap faceView = ( new_decode(new File(filePath)));
        soloPic.setImageBitmap(faceView);

        savePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File image = new File(filePath);
                image.mkdirs();
                Toast.makeText(getApplicationContext(),"Picture is successfully saved", Toast.LENGTH_LONG).show();

                Intent back = new Intent(getApplicationContext(), MainInterface.class);
                startActivity(back);
            }
        });

        uploadFlickr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("In", "the intent");

                File file = new File(filePath);
                Uri phototUri = Uri.fromFile(file);
                ArrayList<Uri> uris = new ArrayList<>(); // absence of this cause bug 7.6.1
                uris.add(phototUri);

                Intent upload = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    Intent back = new Intent(getApplicationContext(), MainInterface.class);
                    startActivity(back);

                Log.d("File path is here", filePath);

                upload.setData(phototUri);
                upload.setType("image/*");
                upload.putExtra(Intent.EXTRA_STREAM, uris);
                    upload.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{back});

                startActivity(Intent.createChooser(upload, "Share Image"));
            }
        });
    }

    public static Bitmap new_decode(File f) {
        // decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inDither = false; // Disable Dithering mode

        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared

        o.inInputShareable = true; // Which kind of reference will be used to
        // recover the Bitmap data after being
        // clear, when it will be used in the future
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 300;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 1.5 < REQUIRED_SIZE && height_tmp / 1.5 < REQUIRED_SIZE)
                break;
            width_tmp /= 1.5;
            height_tmp /= 1.5;
            scale *= 1.5;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o.inDither = false;
        o.inPurgeable = true;
        o.inInputShareable = true;
        try {

            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            System.out.println(" IW " + width_tmp);
            System.out.println("IHH " + height_tmp);
            int iW = width_tmp;
            int iH = height_tmp;

            return Bitmap.createScaledBitmap(bitmap, iW, iH, true);

        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            e.printStackTrace();
            // clearCache();

            // System.out.println("bitmap creating success");
            System.gc();
            return null;
            // System.runFinalization();
            // Runtime.getRuntime().gc();
            // System.gc();
            // decodeFile(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
