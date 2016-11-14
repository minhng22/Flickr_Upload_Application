package com.example.tpla.android_oauth_flickr.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "photos")
public class FlickrPhoto extends Model {
	@Column(name = "uid")
	private String uid;
	@Column(name = "name")
	private String name;
	@Column(name = "url")
	private String url;

	public FlickrPhoto() {
		super();
	}

	public FlickrPhoto(JSONObject object){
		super();
		try {
			this.uid = object.getString("id");
			this.name = object.getString("title");
			// http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
			this.url = "http://farm" + object.getInt("farm") + ".staticflickr.com/" + object.getInt("server") +
					"/" + uid + "_" + object.getString("secret") + ".jpg";
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static FlickrPhoto byPhotoUid(String uid) {
		return new Select().from(FlickrPhoto.class).where("uid = ?", uid).executeSingle();
	}

	public static ArrayList<FlickrPhoto> recentItems() {
		ArrayList<FlickrPhoto> recentPhotos = new ArrayList<FlickrPhoto>();
		recentPhotos = new Select().from(FlickrPhoto.class).orderBy("id DESC").limit("3000").execute();
        return recentPhotos;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}
}