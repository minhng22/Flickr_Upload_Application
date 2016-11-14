package com.example.tpla.android_oauth_flickr;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class TakePicture extends android.support.v4.app.Fragment{
    private static final int CAMERA_REQUEST = 1888;

    static String str_Camera_Photo_ImagePath = "";
    private static File f;
    private static int Take_Photo = 2, create_File= 3;
    private static String str_randomnumber = "";
    static String str_Camera_Photo_ImageName = "";
    public static String str_SaveFolderName;
    private static File wallpaperDirectory;
    Bitmap bitmap;
    int storeposition = 0;
    public static GridView gridview;
    public static ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.takepic_frag, container, false);

        Button photoButton = (Button) v.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDir();
            }
        });
        return v;}

    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public void createDir(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 3);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == create_File && resultCode == RESULT_OK) {
            Uri treeUri = data.getData();
            str_Camera_Photo_ImagePath = FileUtil.getFullPathFromTreeUri(treeUri,getActivity().getApplicationContext());
            f= new File(str_Camera_Photo_ImagePath);
            Log.e("LOG","file path on AR:  "+ str_Camera_Photo_ImagePath);

            if (!f.exists()) {Log.e("Error", "Directory not created"); }
            if(!isExternalStorageWritable()){Log.e("Error", "SD card not writable");}

            /*Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                    "com.example.android.fileprovider",
                    f);*/
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Take_Photo);
            System.err.println("f  " + f);
        }

        if (requestCode == Take_Photo) {
            String filePath = null;

            filePath = str_Camera_Photo_ImagePath;
            if (filePath != null) {
                if (f.exists()){
                    Intent choose = new Intent(getActivity(), ViewImage3.class);
                    Bundle sendData = new Bundle();
                    sendData.putString("File path", filePath);
                    sendData.putString("File name", str_Camera_Photo_ImageName);
                    choose.putExtra("datas", sendData);
                    startActivity(choose);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please take a picture", Toast.LENGTH_LONG).show();
                    Intent back = new Intent(getActivity().getApplicationContext(), MainInterface.class);
                    startActivity(back);}}
            else {
                bitmap = null;
            }
        }
    }
}

