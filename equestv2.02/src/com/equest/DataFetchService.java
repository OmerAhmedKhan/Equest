package com.equest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.constants.constants;
import com.equest.network.URLs;

public class DataFetchService extends IntentService {

	public DataFetchService() {
		super("DataFetchService");
		// TODO Auto-generated constructor stub
	}

	JSONObject jsonObj;
	JSONArray jsonArray;
	Handler handler;
	String Result, errorMessage;
	String easyScore, mediumScore, hardScore, totalCorrect, totalEasy,
			totalMedium, totalHard, totalScore, totalQuestions;
	String[] userIds;

	Integer Response;
	List<NameValuePair> POST = new ArrayList<NameValuePair>();

	// //////////////////////////////////////////////////////////

	private class getResult_async extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... params) {

			Result = Network.makeHttpRequest(getApplicationContext(),
					URLs.Base_URL, "POST", POST).toString();
			Log.e("IntentServiceJSON", Result);

			// yaha data entry krni h Data base m

			try {
				jsonObj = new JSONObject(Result);
				// Response = Integer.parseInt(jsonObj.getJSONObject("header")
				// .getString("code"));

				errorMessage = jsonObj.getJSONObject("header").getString(
						"message");

				jsonObj = jsonObj.getJSONObject("body");

				jsonObj = jsonObj.getJSONObject("result");
				jsonArray = jsonObj.getJSONArray("user");

				userIds = constants.user_id_for_results.split(",");
				EquestDB.deleteAllResults(DataFetchService.this);
				// Log.e("UserIDs in SErvice", "" +
				// constants.user_id_for_results.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jobj = jsonArray.getJSONObject(i);

					totalQuestions = jobj.getString("total_questions");
					totalCorrect = jobj.getString("total_correct");
					totalScore = jobj.getString("total_score");
					totalEasy = jobj.getString("total_easy");
					totalMedium = jobj.getString("total_medium");
					totalHard = jobj.getString("total_hard");
					easyScore = jobj.getString("easy_score");
					mediumScore = jobj.getString("medium_score");
					hardScore = jobj.getString("hard_score");

					// yaha data entry krni h database m

					/*********************************************************************************************/

					EquestDB.insertResult(getApplicationContext(), userIds[i],
							totalQuestions, totalCorrect, totalScore,
							totalEasy, totalMedium, totalHard, easyScore,
							mediumScore, hardScore);
					/*********************************************************************************************/
				}

				// return Result;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Result;

		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
		}

	}

	// ///////////////////////////////////////////////////////////

	// Handles the intent when service is called
	@Override
	protected void onHandleIntent(Intent intent) {

		// Gets the text to be displayed as a notification
		Log.e("IntentServiceJSON", "in Service");
		handler = new Handler();

		POST.add(new BasicNameValuePair("functionName", "getResults"));
		POST.add(new BasicNameValuePair("user_id",
				constants.user_id_for_results));
		new getResult_async().execute();

	}

}