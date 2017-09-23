package com.equest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.constants.constants;
import com.equest.network.URLs;

public class showAllUsers extends BaseActivity {

	ListView list;
	EditText search;
	Button Buttnogetdata;
	TextView noMatch;
	ArrayList<AllUsersEntity> usersList;

	String[] userTableColunms = new String[7];
	String[] userIds;
	/* TextView actionBar; */

	

	private static final String TAG_body = "body";
	private static final String TAG_userid = "user_id";
	private static final String TAG_userfirstname = "user_fname";
	private static final String TAG_userlastname = "user_lname";
	private static final String TAG_user_information = "user_information";
	private static final String TAG_user_email_id = "email_id";
	// private static final String TAG_user_password = "password";

	JSONArray jArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_listview_sections);

		usersList = new ArrayList<AllUsersEntity>();

		noMatch = (TextView) findViewById(R.id.nomatch);
		list = (ListView) findViewById(R.id.listusers);
		search = (EditText) findViewById(R.id.edtTxt);

		if (constants.isLogOffline == false) {
			new ViewUsersList().execute();

		} else {

			SharedPreferences getDateSP = getSharedPreferences("equest_prefs",
					Activity.MODE_PRIVATE);
			constants.user_id_for_results = getDateSP.getString("userIds", "");

			userIds = constants.user_id_for_results.split(",");
			//Log.e("userIDSLength", String.valueOf(userIds.length));

			for (int i = 0; i < userIds.length; i++) {
				AllUsersEntity obj = new AllUsersEntity();
				// Log.e("userID"+i,userIds[i]);
				userTableColunms = EquestDB.getUserByID(showAllUsers.this,
						userIds[i]);
				obj.setUser_fname(userTableColunms[2]);
				obj.setUser_lname(userTableColunms[3]);
				obj.setEmail_id(userTableColunms[4]);

				usersList.add(obj);
			}

			// obj.setUser_fname("Omer");
			// obj.setUser_lname("Ahmed");
			
			//
			//
			// Log.d("SQLDATARETURN",
			// String.valueOf(EquestDB.getUserByID(showAllUsers.this,
			// "2").length));
			// test=EquestDB.getUserByID(showAllUsers.this, "36");
			// Log.d("SQLDATA",test[4]);
			// usersList.add(obj);

			list = (ListView) findViewById(R.id.listusers);
			TempListAdapter adapter = new TempListAdapter(usersList);
			list.setAdapter(adapter);

		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// arg1 is position

				AllUsersEntity itemPosition = (AllUsersEntity) list
						.getItemAtPosition(arg2);

				constants.user_email = itemPosition.getEmail_id();

				// constants.password=itemPosition.getPassword();

				if (constants.isLogOffline == false) {
					final Dialog dialog = new Dialog(showAllUsers.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.dialog_box);
					// dialog.setTitle("Title...");
					TextView text = (TextView) dialog.findViewById(R.id.text);

					text.setText("Please select any one to view");
					ImageView image = (ImageView) dialog
							.findViewById(R.id.image);
					image.setImageResource(R.drawable.logo_dialogbox);

					TextView dialogtextviewone = (TextView) dialog
							.findViewById(R.id.dialogButtonResults);
					TextView dialogtextviewtwo = (TextView) dialog
							.findViewById(R.id.dialogButtonTestReview);

					dialogtextviewtwo
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {

									Intent ourIntent = new Intent(
											showAllUsers.this,
											MainActivityResult.class);
									startActivity(ourIntent);

								}// onClick

							});

					dialogtextviewone
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {

									// add alis activity
									Intent ourIntent = new Intent(
											showAllUsers.this,
											getAllUserLog.class);
									startActivity(ourIntent);

								}// onClick

							});

					dialog.show();
				} else {
					// Yaha list populate krni h Database se data fetch kr k

					Intent intent = new Intent(showAllUsers.this,
							OfflineResults.class);
					intent.putExtra("userId", userIds[arg2]);
					startActivity(intent);

//					Toast.makeText(showAllUsers.this, "Shukr",
//							Toast.LENGTH_SHORT).show();
				}

			}// ItemClick Listner ends
		});

		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int textlength = search.length();
				ArrayList<AllUsersEntity> array_sort = new ArrayList<AllUsersEntity>();

				for (int i = 0; i < usersList.size(); i++) {

					String username = usersList.get(i).getUser_fname() + " "
							+ usersList.get(i).getUser_lname() + "  ";

					// + " " +( (AllUsersEntity) list.getItemAtPosition(i)
					// ).getUser_lname();
					if (username.contains(search.getText().toString())) {
						array_sort.add(usersList.get(i));
					}// if ends

				}// for ends

				list.setAdapter(new TempListAdapter(array_sort));
				if (textlength == 0) {

					list.setAdapter(new TempListAdapter(usersList));
				}// IF ENDS

				if (list.getCount() > 0)
					findViewById(R.id.nomatch).setVisibility(View.GONE);
				else {
					findViewById(R.id.nomatch).setVisibility(View.VISIBLE);
				}// else ends

			}// afterTextChanged ends

			@Override
			public void afterTextChanged(Editable s) {
				// NOTHING

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// NOTHING

			}

		});

		Buttnogetdata = (Button) findViewById(R.id.finish_button);
		Buttnogetdata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();

				// usersList = new ArrayList<AllUsersEntity>();
				// new ViewUsersList().execute();

			}
		});

	}

	private class ViewUsersList extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(showAllUsers.this);
			progressDialog.setMessage("Getting User list please wait ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("functionName", "getAllUserLog"));

			String jsonResponse = Network.makeHttpRequest(showAllUsers.this,
					URLs.Base_URL, "POST", params);

			

			JSONObject json = null;

			try {
				json = new JSONObject(jsonResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			progressDialog.dismiss();
			if (json == null)
				return;

			try {
				jArray = json.getJSONObject(TAG_body).getJSONArray(
						TAG_user_information);

				EquestDB.deleteAllUsers(showAllUsers.this);

				constants.user_id_for_results = "";

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jobj = jArray.getJSONObject(i);

					String userFirstname = jobj.getString(TAG_userfirstname);
					String userLasttname = jobj.getString(TAG_userlastname);
					String userEmailId = jobj.getString(TAG_user_email_id);
					String userid = jobj.getString(TAG_userid);

					if (constants.user_id_for_results == "")
						constants.user_id_for_results = userid;
					else
						constants.user_id_for_results = constants.user_id_for_results
								+ "," + userid;

					// yaha bhi entry krni h database m
					EquestDB.insertUser(showAllUsers.this, userid,
							userFirstname, userLasttname, userEmailId, " ");

					AllUsersEntity obj = new AllUsersEntity();

					obj.setUser_fname(userFirstname);
					obj.setUser_lname(userLasttname);
					obj.setEmail_id(userEmailId);

					// obj.setPassword(userPassword);
					//
					// obj.setUser_id(userId);

					

					usersList.add(obj);
				}

				Intent intent_service = new Intent(showAllUsers.this,
						DataFetchService.class);
				startService(intent_service);

				SharedPreferences setDateSP = getSharedPreferences(
						"equest_prefs", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = setDateSP.edit();
				editor.putString("userIds", constants.user_id_for_results);
				editor.commit();

				//Log.d("Omer", "" + constants.user_id_for_results.toString());
				
				//Creates an Intent to start the Notification service
				Intent myserviceintent = new Intent(getApplicationContext(), NotificationService.class);
				
				
				
				// Starts the service
				startService(myserviceintent);
				
				list = (ListView) findViewById(R.id.listusers);
				TempListAdapter adapter = new TempListAdapter(usersList);
				list.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public class TempListAdapter extends BaseAdapter {
		private ArrayList<AllUsersEntity> userArrayList;

		public TempListAdapter(ArrayList<AllUsersEntity> arrayList) {
			this.userArrayList = arrayList;
		}

		public int getCount() {
			return userArrayList.size();
		}

		public Object getItem(int position) {
			return userArrayList.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		//
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row;
			ViewHolder viewHolder;
			if (convertView == null) {
				row = inflater.inflate(R.layout.show_all_users_row, null);
				viewHolder = new ViewHolder();

				viewHolder.lstname = (TextView) row
						.findViewById(R.id.textView2);

				row.setTag(viewHolder);
			}

			else {
				row = convertView;
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.lstname.setText(userArrayList.get(position)
					.getUser_fname()
					+ " "
					+ userArrayList.get(position).getUser_lname());

			return row;
		}

		class ViewHolder {
			// TextView userid;
			TextView frstname;
			TextView lstname;

		}

	}// adapter ends

	/********************************************************************************************************/

}//
