package com.example.jsntest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	public static final String JSON_REQUEST_URL = "http://goodgame.ru/api/getchannelstatus?"
			+ "id=Abver,Miker,Peptar,babymagik,Happa_,pzn,FeI1x&fmt=json";

	ListView myList;
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> myArrList;
	myAdapter adapter;
	String streamers[] = { "1053" /* Abver */, "11577" /* Peptar */,
			"5" /* Miker */, "1105" /* babymagic */, "2059" /* happa */,
			"12949" /* pzn */, "21506" /* FeI1x */};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myList = (ListView) findViewById(R.id.list);

		myArrList = new ArrayList<HashMap<String, Object>>();

		new MyTask().execute();
	}

	private class MyTask extends AsyncTask<Void, Void, String> {

		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		String strJson = "";

		@Override
		protected String doInBackground(Void... params) {
			JSONObject JsonObj = null;

			try {
				URL url = new URL(JSON_REQUEST_URL);

				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();

				InputStream inputStream = urlConnection.getInputStream();
				StringBuffer buffer = new StringBuffer();

				reader = new BufferedReader(new InputStreamReader(inputStream));

				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}

				strJson = buffer.toString();
				// Log.d("MyLog", strJson);

				try {
					JsonObj = new JSONObject(strJson);
					for (int i = 0; i < streamers.length; i++) {
						JSONObject streamer = JsonObj
								.getJSONObject(streamers[i]);

						if (streamer.getString("status").equals("Live")) {
							Log.d("MyLog", "Streamer: " + streamer.getString("key")
									+ " Live");
							map = new HashMap<String, Object>();
							map.put("image", streamer.getString("thumb"));
							map.put("name", streamer.getString("key"));
							map.put("titel", streamer.getString("title"));
							map.put("viewers", streamer.getString("viewers"));
							myArrList.add(map);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String strJson) {
			super.onPostExecute(strJson);

			adapter = new myAdapter(MainActivity.this, myArrList);

			myList.setAdapter(adapter);
		}
	}
}
