package com.example.tpla.android_oauth_flickr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tpla.android_oauth_flickr.models.FlickrPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PhotoArrayAdapter extends ArrayAdapter<FlickrPhoto> {
    private Context mcontext = getContext();

    public PhotoArrayAdapter(Context context, List<FlickrPhoto> photoList) {
        super(context, R.layout.photo_item, photoList);
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		FlickrPhoto photo = this.getItem(position);
		LinearLayout itemView;

		ImageView ivImage;
		ImageLoader imageLoader = ImageLoader.getInstance();
        if (convertView == null) {
    		LayoutInflater inflator = LayoutInflater.from(getContext());
    		itemView = (LinearLayout) inflator.inflate(R.layout.photo_item, parent, false);
        } else {
            itemView = (LinearLayout) convertView;
        }
        ivImage = (ImageView) itemView.findViewById(R.id.ivPhoto);
        ivImage.setImageResource(android.R.color.transparent); 
        imageLoader.displayImage(photo.getUrl(), ivImage);

        return itemView;
	}
}
