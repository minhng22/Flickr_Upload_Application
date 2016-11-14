package com.example.tpla.android_oauth_flickr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;


// In this case, the fragment displays simple text based on the page
public class Library extends Fragment {
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView grid;
    GridViewAdapter adapter;
    File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_photos, container, false);


        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d("Error! No SDCARD Found!", "error");
        } else {
            // Locate the image folder in your SD Card
            String secStore = System.getenv("SECONDARY_STORAGE");

            file = new File(secStore+ File.separator + "DCIM"+File.separator+"Flickr");
            if (file == null)
            {file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "DCIM"+File.separator+"Flickr");}
            file.mkdirs();
            Log.d("finds file in SD", "no err");
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }
        }

        grid = (GridView) view.findViewById(R.id.gridView);

        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);

        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getActivity(), ViewImage.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", FilePathStrings);
                // Pass String arrays FileNameStrings
                i.putExtra("filename", FileNameStrings);
                // Pass click position
                i.putExtra("position", position);
                startActivity(i);
            }
        });
        return view;
    }
}