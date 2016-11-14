package com.example.tpla.android_oauth_flickr;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

public class FlickClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "http://www.flickr.com/services";
    public static final String REST_CONSUMER_KEY = "1bcb964f69eb8715b85dae1fbd2501ca";
    public static final String REST_CONSUMER_SECRET = "2f5bb6c66c85f471";
    public static final String REST_CALLBACK_URL = "oauth://damn";

    //https://www.flickr.com/photos/134098181@N07/
    
    public FlickClient (Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        String userID;
        String OAuthtoken;
        String apiSig;
        /*setBaseUrl("https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&" +
                "api_key=b4c7a1f3ee0b6849e7f2987c24e90bdd&format=json&nojsoncallback=1&" +
                "auth_token=72157653046060514-6e636a9e2137e06f&api_sig=bc4d87e18a4d1fe58fb79c0ef277c84b"); */
        setBaseUrl("https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=23c74174a0d278304b7ff573de3560a0&format=json&nojsoncallback=1&auth_token=72157655145878198-cf9aef2044e44284&api_sig=3fa9bb72cdc8de60eae2a6a3add851e4");
    }
    // get Public Photos

    public void getPhotos (AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }
}
