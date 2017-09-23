package com.equest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Network {
	static InputStream inputstream = null;

	// Create default http client
	static DefaultHttpClient httpClient;
	static HttpPost httpPost;
	static HttpGet httpGet;
	static HttpEntity entity;
	static HttpParams httpParams;
	static HttpResponse httpResponse;
	
	public static int NETWORK_CONNECT_TIMEOUT = 60000;
	public static int NETWORK_READ_TIMEOUT = 60000;
	
	
	public static String makeHttpRequest(Context context, String url, String method,
			List<NameValuePair> params) {
		if (isConnected(context) == true) {
			// Making HTTP request
			try {
				// defaultHttpClient
				httpClient = new DefaultHttpClient();
				httpParams = httpClient.getParams();

				HttpConnectionParams.setConnectionTimeout(httpParams,
						NETWORK_CONNECT_TIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParams,
						NETWORK_READ_TIMEOUT);
				httpClient.setParams(httpParams);

				// check for request method
				if (method.equals("POST")) {
					Log.i("URL_POST", "" +url);
					// request method is POST
					httpPost = new HttpPost(url);

					// Set Character
					entity = new UrlEncodedFormEntity(params, "utf8");

					httpPost.setEntity(entity);
					Log.i("params","" + params.toString());

					httpResponse = httpClient.execute(httpPost);
					entity = httpResponse.getEntity();
					if (entity != null) {
						inputstream = entity.getContent();

						String resultstring = convertStreamToString(inputstream);

						inputstream.close();

						return resultstring;
					}

				} 

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
	
	private static String convertStreamToString(InputStream is) throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}

		} catch (Exception e) {
			// Log.i("SOCIAL_APP", e.getMessage());
		} finally {
			if (rd != null) {
				rd.close();
				rd = null;
			}
		}
		return total.toString();
	}

	/**
	 * Get the network info
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * Check if there is any connectivity
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected());
	}

	/**
	 * Check if there is any connectivity to a Wifi network
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static boolean isConnectedWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
	}

	/**
	 * Check if there is any connectivity to a mobile network
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static boolean isConnectedMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	}
}
