package com.example.jsntest;

import java.util.List;
import java.util.Map;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class myAdapter extends SimpleAdapter {
	Context context;
	List<? extends Map<String, ?>> data;

	public myAdapter(Context context, List<? extends Map<String, ?>> data) {
		super(context, data, 0, null, null);
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Map<String, ?> map = data.get(position);
		
		if (convertView == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.list_form, parent, false);
		}
		TextView nameOfStreamer = (TextView) convertView
				.findViewById(R.id.NameOfStreamer);
		TextView titel = (TextView) convertView.findViewById(R.id.title);
		TextView viewers = (TextView) convertView.findViewById(R.id.viewers);
		ImageView screen = (ImageView) convertView.findViewById(R.id.imageView);

		nameOfStreamer.setText(map.get("name").toString());
		titel.setText(map.get("titel").toString());
		viewers.setText(map.get("viewers").toString());
		Picasso.with(context).load(map.get("image").toString()).into(screen);
		
		return convertView;
	}
}
