package com.example.tpla.android_oauth_flickr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.tpla.android_oauth_flickr.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhotosActivity extends Fragment implements OnItemClickListener {
    FlickrPhoto p;
    FlickClient client;
    final String PREF_NAME = "abc";
    ArrayList<FlickrPhoto> photoItems;
    GridView gvPhotos;
    PhotoArrayAdapter adapter;
    JSONArray photos;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_photos, container, false);

        client = FlickrClientApp.getRestClient();
        photoItems = new ArrayList<>();
        gvPhotos = (GridView) view.findViewById(R.id.gvPhotos);
        adapter = new PhotoArrayAdapter(getActivity(), photoItems);
        gvPhotos.setAdapter(adapter);
        loadPhotos();
        gvPhotos.setOnItemClickListener(this);

        return view;
    }

    public void loadPhotos() {
        client.getPhotos(new JsonHttpResponseHandler() {
            public void onSuccess(JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
                    photos = json.getJSONObject("photos").getJSONArray("photo");
                    //test null
                    if (photos ==null) Log.e("Error","Null array");
                    //end test
                    for (int x = 0; x < photos.length(); x++) {
                        String uid = photos.getJSONObject(x).getString("id");
                        if (uid == null){Log.e("Error","Null photoes");}
                        p = FlickrPhoto.byPhotoUid(uid); // put id in and get photo out
                        if (p == null) {
                            Log.e("Error","Null photoes");
                            p = new FlickrPhoto(photos.getJSONObject(x));}
                        p.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("debug", e.toString());
                }
                // Load into GridView from DB
                for (FlickrPhoto p : FlickrPhoto.recentItems()) {
                    adapter.add(p);
                }
                Log.d("DEBUG", "Total: " + photoItems.size());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0,
                            View arg1, int arg2, long arg3) {
        try {
            showdetail(arg2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showdetail(int posistion) throws JSONException {
        Intent soloPic = new Intent ( getActivity(), ViewImage2.class);

        startActivity(soloPic);

        FlickrPhoto photo = adapter.getItem(posistion);
        String newUrl = photo.getUrl();
        Log.d("new URL", newUrl);
        Log.d("position", PREF_NAME.toString());

        String uid = photos.getJSONObject(posistion).getString("id"); // return wrong photo so just put in to save bug

        SharedPreferences data = getActivity().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = data.edit();
        editor.putString("id",uid);
        editor.putString("url", newUrl);
        editor.commit();
    }
}
