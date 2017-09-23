package com.equest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.constants.constants;
import com.equest.network.URLs;

public class getAllUserLog extends Activity {
	ArrayList<severityScoreEntity> userLog;
	List<NameValuePair> serviceCall;

	JSONObject body;
	JSONObject section;
	JSONObject subject;
	JSONObject severityLevel;
	Context context;
	String serviceResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_review_list);

		// add layout
		context = this;
		userLog = new ArrayList<severityScoreEntity>();
		serviceCall = new ArrayList<NameValuePair>();
		new UserLog().execute();

	}

	private class UserLog extends
			AsyncTask<Void, Void, ArrayList<severityScoreEntity>> {
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
		protected ArrayList<severityScoreEntity> doInBackground(Void... arg0) {
			serviceCall.add(new BasicNameValuePair("functionName",
					"report_second_activity"));
			serviceCall.add(new BasicNameValuePair("email",
					constants.user_email));
			// serviceCall.add(new BasicNameValuePair("password", "1234"));
			serviceResponse = Network.makeHttpRequest(context, URLs.Base_URL,
					"POST", serviceCall);
			JSONObject section;
			userLog.clear();
			try {
				Log.e("serviceResponse", serviceResponse);
				JSONObject body = new JSONObject(serviceResponse);
				body = body.getJSONObject("body");
				section = body.getJSONObject("sections");

				Log.e("section.names()", section.names().toString());

				JSONArray keys = section.names();
				severityScoreEntity.setSectionSize(keys.length());
				for (int index = 0; index < keys.length(); index++) {
					JSONObject subject = section.getJSONObject(keys
							.getString(index));
					severityScoreEntity.setSectionName(keys.getString(index),
							index);
					Log.e("severityScoreEntity",
							severityScoreEntity.retSectionName(index) + "\n");
					severityScoreEntity score = new severityScoreEntity();
					JSONObject severity = subject.optJSONObject("easy");
					if (severity != null)
						score.setEasy((keys.getString(index)).trim(),
								severity.getString("correct_questions"),
								severity.getString("total_questions"));
					severity = subject.optJSONObject("medium");
					if (severity != null)
						score.setMedium((keys.getString(index)).trim(),
								severity.getString("correct_questions"),
								severity.getString("total_questions"));
					severity = subject.optJSONObject("hard");
					if (severity != null)
						score.setHard((keys.getString(index)).trim(),
								severity.getString("correct_questions"),
								severity.getString("total_questions"));
					userLog.add(score);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e("USER LOG", "" + userLog.toString());

			return userLog;
		}

		@Override
		protected void onPostExecute(ArrayList<severityScoreEntity> Questions)

		{
			progressDialog.dismiss();
			// Log.e("user LOG", userLog.toString());
			Log.e("USER LOG", "" + userLog.toString());

			ListView list = (ListView) findViewById(R.id.my_list);
			// ListView list = (ListView)
			// findViewById(R.id.test_review_sections_detail_sectiontxtview);
			TempListAdapters adapter = new TempListAdapters(userLog);
			list.setAdapter(adapter);

		}

	}

	public class TempListAdapters extends BaseAdapter

	{
		int index;
		// int threeTimes;

		private ArrayList<severityScoreEntity> arrayList;

		public TempListAdapters(ArrayList<severityScoreEntity> arrayList)

		{

			this.arrayList = arrayList;
			index = -1;
			// threeTimes=0;

		}

		public int getCount() {

			return arrayList.size();

		}

		public Object getItem(int position) {

			return arrayList.get(position);
		}

		public long getItemId(int position) {

			return 0;

		}

		public View getView(int position, View convertView, ViewGroup parent)

		{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row;
			ViewHolder viewholder;

			// View row = convertView;
			// ViewHolder viewholder;
			// View convertView = null;

			if (convertView == null) {

				row = inflater.inflate(R.layout.test_review_sections_detail,
						null);

				viewholder = new ViewHolder();

				viewholder.sections = (TextView) row
						.findViewById(R.id.test_review_sections_detail_sectiontxtview);

				viewholder.eng_easy = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);

				viewholder.eng_medium = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.eng_hard = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);

				viewholder.maths_easy = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.maths_medium = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.maths_hard = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);

				viewholder.iq_easy = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.iq_medium = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_easydynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_meduimdynamictxtview);

				viewholder.iq_hard = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);

				viewholder.correct_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);
				viewholder.total_questions = (TextView) row
						.findViewById(R.id.test_review_sections_detail_harddynamictxtview);
				viewholder.total_result = (TextView) row
						.findViewById(R.id.test_review_sections_detail_numberofquestxtview);
				viewholder.sections.setTag(row);
				row.setTag(viewholder);

				if (index < severityScoreEntity.retSectionSize()) {
					index++;
				} else {

					index = 0;
				}

			}

			else {
				// row = convertView;
				row = convertView;
				viewholder = (ViewHolder) convertView.getTag();
			}
			try {

				Log.d("Salik",
						"index " + index + " |  "
								+ arrayList.get(position).retSectionName(index));
				viewholder.sections.setText(arrayList.get(position)
						.retSectionName(index));

				// if(index==severityScoreEntity.retSectionSize()-1)
				// {
				// viewholder.sections.setText(null);
				// viewholder.total_result.setText(null);
				// viewholder.eng_easy.setText(null);
				//
				// viewholder.eng_medium.setText(null);
				//
				// viewholder.eng_hard.setText(null);
				// }

				// Log.e("hello :D :D ",
				// arrayList.get(position).retEasyCorrect(
				// severityScoreEntity.retSectionName(index) + "\n"));
				int correct = Integer.parseInt(arrayList.get(position)
						.retEasyCorrect(
								severityScoreEntity.retSectionName(index)))
						+ Integer.parseInt(arrayList.get(position)
								.retMediumCorrect(
										severityScoreEntity
												.retSectionName(index)))
						+ Integer.parseInt(arrayList.get(position)
								.retHardCorrect(
										severityScoreEntity
												.retSectionName(index)));

				int total = Integer
						.parseInt(arrayList.get(position).retEasyTotal(
								severityScoreEntity.retSectionName(index)))
						+ Integer.parseInt(arrayList.get(position)
								.retMediumTotal(
										severityScoreEntity
												.retSectionName(index)))
						+ Integer.parseInt(arrayList.get(position)
								.retHardTotal(
										severityScoreEntity
												.retSectionName(index)));

				viewholder.total_result.setText(correct + " out of " + total);
				viewholder.eng_easy.setText(arrayList.get(position)
						.retEasyCorrect(
								severityScoreEntity.retSectionName(index))
						+ "/"
						+ arrayList.get(position).retEasyTotal(
								severityScoreEntity.retSectionName(index)));

				viewholder.eng_medium.setText(arrayList.get(position)
						.retMediumCorrect(
								severityScoreEntity.retSectionName(index))
						+ "/"
						+ arrayList.get(position).retMediumTotal(
								severityScoreEntity.retSectionName(index)));

				viewholder.eng_hard.setText(arrayList.get(position)
						.retHardCorrect(
								severityScoreEntity.retSectionName(index))
						+ "/"
						+ arrayList.get(position).retHardTotal(
								severityScoreEntity.retSectionName(index)));

				Log.e("index", "" + index + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * if(index==2) {
			 * 
			 * index=6;
			 * 
			 * }
			 */
			if (index == severityScoreEntity.retSectionSize() - 1) {

				index = severityScoreEntity.retSectionSize();

			}

			return row;
		}

		class ViewHolder {

			TextView sections;

			TextView eng_easy;
			TextView eng_medium;
			TextView eng_hard;

			TextView maths_easy;
			TextView maths_medium;
			TextView maths_hard;

			TextView iq_easy;
			TextView iq_medium;
			TextView iq_hard;

			TextView correct_questions;
			TextView total_questions;
			TextView total_result;

		}// ViewHolder ends

	}// adapter ends

}// activity ends
