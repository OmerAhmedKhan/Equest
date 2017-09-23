package com.equest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.constants.constants;
import com.equest.network.URLs;

public class Login extends Activity {

	List<NameValuePair> UserData = new ArrayList<NameValuePair>();
	private Context context;
	JSONObject json;
	String email_id, password, Result, errorMessage = "";
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	Button btn_login;
	EditText email, pass;
	int response, role_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		email = (EditText) findViewById(R.id.et_email_login);
		pass = (EditText) findViewById(R.id.et_password_login);

		btn_login = (Button) findViewById(R.id.btn_login);

		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				email_id = email.getText().toString();
				password = pass.getText().toString();

				Log.e("email", email_id.toString());
				Log.e("pass", password.toString());

				if (authenticate_givenremail(email_id)) {

					if (password == "") {

						Toast.makeText(getApplicationContext(),
								"Password field is empty ", Toast.LENGTH_SHORT)
								.show();

					}// if ends
					else {
						Log.e("going async", "here");
						new login_async().execute();

					}// else ends
				}// if ends top

				else {
					Toast.makeText(getApplicationContext(),
							"Invalid email address", Toast.LENGTH_SHORT).show();
				}// else ends

			} // onclick

		});
	} // Oncreate ends
		// ////////////////////////////////////////////////////

	public Login()// constructor
	{
		context = this;

	}

	// ////////////////////////////////////////////////////
	public String getUserAuthentication(String FunctionName, String UserName,
			String Password) {
		UserData.add(new BasicNameValuePair("functionName", FunctionName));
		UserData.add(new BasicNameValuePair("email", UserName));
		UserData.add(new BasicNameValuePair("password", Password));
		return Network
				.makeHttpRequest(context, URLs.Base_URL, "POST", UserData)
				.toString();
	}

	// ////////////////////////////////////////////////////

	public boolean authenticate_givenremail(String email) {
		if (email.matches(emailPattern) && email.length() > 0) {
			return true;
		} else

			return false;

	}

	// /////////////////////////////////////////////
	// async class to get json

	private class login_async extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("Loading...");
			progressDialog.show();

		}

		@Override
		protected String doInBackground(Void... params) {

			Result = ((getUserAuthentication("login", email_id, password)));
			Log.e("json", Result);
			Log.e("hello", "hellooo!!!");
			try {
				json = new JSONObject(Result);
				response = Integer.parseInt(json.getJSONObject("header")
						.getString("code"));

				errorMessage = json.getJSONObject("header")
						.getString("message");

				json = json.getJSONObject("body");

				json = json.getJSONObject("user");
				Log.e("user_json", json.getString("user_id").toString());

				role_id = Integer.parseInt(json.getString("role_id"));
				constants.roleId = role_id;
				constants.user_id = json.getString("user_id").toString();
				constants.password = json.getString("password").toString();
				;
				constants.user_email = json.getString("user_fname").toString()
						+ " " + json.getString("user_lname").toString();

				return Result;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Result;

		}

		@Override
		protected void onPostExecute(String result) {

			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			// Log.e("response", String.valueOf(response));
			// Log.e("user_id", constants.user_id);
			if ((response == 0) && (role_id == 1)) {

				Intent intent_student = new Intent(Login.this,
						instructions_activity.class);
				constants.index = 0;
				// intent.putExtra("user_id", constants.user_id);
				// intent.putExtra("user_name", constants.user_name);
				startActivity(intent_student);
				finish();
			} else if ((response == 0) && (role_id == 2)) {

				// constants.isAdmin=true;

				if (constants.isAdmin.equals("false")) {
					//Log.e("constants.isAdmin in Login", constants.isAdmin);
					SharedPreferences setDateSP = getSharedPreferences(
							"equest_prefs", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = setDateSP.edit();
					editor.putString("isAdmin", "true");
					editor.commit();
				}

				Intent intent_admin = new Intent(Login.this, showAllUsers.class);

				startActivity(intent_admin);
				finish();
			}

			else {

				Toast.makeText(getApplicationContext(), errorMessage,
						Toast.LENGTH_LONG).show();

			}
			super.onPostExecute(result);
		}

	}
	// /////////////////////////////////////////////
	// async class end

}
