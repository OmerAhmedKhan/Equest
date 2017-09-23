package com.equest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import com.constants.constants;

public class Splash extends Activity {
	MediaPlayer playerSplashScreen;

	private int SPLASH_TIME = 4000; // How long the splash will show (3sec)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		playerSplashScreen = MediaPlayer.create(Splash.this, R.raw.nice);
		playerSplashScreen.start();
		new splashExectuer().execute();

	} // Oncreate ends

	private class splashExectuer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}// onPreExecute ends

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(SPLASH_TIME);
			} catch (Exception e) {
			}

			return null;
		}// doInBackground ends

		@Override
		protected void onPostExecute(Void result) {
			Intent intent;

			SharedPreferences getDateSP = getSharedPreferences("equest_prefs",
					Activity.MODE_PRIVATE);
			String Role = getDateSP.getString("isAdmin", "false");

			constants.isAdmin = Role;

			if (constants.isAdmin.equals("true"))
				intent = new Intent(Splash.this, SelectLogin.class);
			else
				intent = new Intent(Splash.this, Login.class);

			// Intent intent = new Intent(Splash.this, Login.class);
			startActivity(intent);
			playerSplashScreen.release();
			finish();
			super.onPostExecute(result);

		} // onPostExecute ends

	}// splashExectuer ends

}// class Splash ends
