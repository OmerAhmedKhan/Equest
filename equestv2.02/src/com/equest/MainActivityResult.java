package com.equest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.constants.constants;
import com.equest.network.URLs;

public class MainActivityResult extends BaseActivity {

	// define variables
	ListView list;

	Button Buttnogetdata;

	ArrayList<ReviewEntity> questionslist;

	private static final String TAG_body = "body";
	private static final String TAG_questions = "questions";
	private static final String TAG_question_descrip = "question_descrip";
	private static final String TAG_answer_descrip = "answer_opt";
	private static final String TAG_answer = "answers";
	/****/
	private static final String TAG_answer_image = "answer_image";
	private static final String TAG_question_image = "question_img";

	String answerDescription;
	String questionImage;

	/**************************************/
	private static final String TAG_is_correct = "is_correct";

	JSONArray jArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_result_listview);
		questionslist = new ArrayList<ReviewEntity>();
		new DetailViewReslt().execute();

		Buttnogetdata = (Button) findViewById(R.id.getdata);
		Buttnogetdata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				questionslist = new ArrayList<ReviewEntity>();
				// new DetailViewReslt().execute();

			}
		});

	}

	private class DetailViewReslt extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(MainActivityResult.this);
			progressDialog.setMessage("Getting test result please wait ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", constants.user_email));
			// params.add(new BasicNameValuePair("password",
			// constants.password));
			params.add(new BasicNameValuePair("functionName",
					"report_first_activity"));

			String jsonResponse = Network.makeHttpRequest(
					MainActivityResult.this, URLs.Base_URL, "POST", params);
			Log.d("Shireen", "" + jsonResponse);
			JSONObject json = null;
			// Getting JSON from URL
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
						TAG_questions);

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject questionObj = jArray.getJSONObject(i);

					// Storing JSON item in a Variable
					String question = questionObj
							.getString(TAG_question_descrip);
					JSONObject answersObj = questionObj
							.getJSONObject(TAG_answer);
					questionImage = questionObj.optString(TAG_question_image);

					answerDescription = answersObj
							.getString(TAG_answer_descrip);
					Log.e("answer", "" + answerDescription);
					int correct = answersObj.getInt(TAG_is_correct);
					String answerImage = answersObj.optString(TAG_answer_image);

					ReviewEntity obj = new ReviewEntity();

					obj.setAnswer_image(answerImage);
					obj.setAnswer_opt(answerDescription);
					obj.setIs_correct(correct);
					obj.setQuestion_description(question);

					obj.setQuestion_image(questionImage);

					questionslist.add(obj);
				}

				list = (ListView) findViewById(R.id.list);
				TempListAdapter adapter = new TempListAdapter(questionslist);
				list.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public class TempListAdapter extends BaseAdapter {
		private ArrayList<ReviewEntity> arrayList;

		public TempListAdapter(ArrayList<ReviewEntity> arrayList) {
			this.arrayList = arrayList;
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

		//
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row;
			ViewHolder viewHolder;
			if (convertView == null) {
				row = inflater.inflate(R.layout.review_row, null);
				viewHolder = new ViewHolder();

				viewHolder.question = (TextView) row
						.findViewById(R.id.txtview_question);

				viewHolder.Answer = (TextView) row
						.findViewById(R.id.txtview_Answer);

				viewHolder.correct_wrong = (ImageView) row
						.findViewById(R.id.imageView_iscorrect);
				viewHolder.answerimg_present = (ImageView) row
						.findViewById(R.id.imageView_answerimg_present);
				viewHolder.questionImage = (ImageView) row
						.findViewById(R.id.imageView_questionimage);

				row.setTag(viewHolder);
			} else {
				row = convertView;
				viewHolder = (ViewHolder) convertView.getTag();
			}

			/*
			 * viewHolder.question.setText(arrayList.get(position)
			 * .getQuestion_description());
			 */

			viewHolder.question.setText((Html.fromHtml(getString(
					R.string.qQuestion,
					(arrayList.get(position).getQuestion_description())))));

			viewHolder.Answer.setText(arrayList.get(position).getAnswer_opt());

			if (arrayList.get(position).getIs_correct() == 1) {
				viewHolder.correct_wrong
						.setImageResource(R.drawable.ic_correct);
			}

			else {

				viewHolder.correct_wrong.setImageResource(R.drawable.ic_wrong);
			}

			// /check if url present

			if (arrayList.get(position)

			.getQuestion_image().equalsIgnoreCase("null")) {
				viewHolder.questionImage.setVisibility(View.INVISIBLE);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						100, 100, 0);

				viewHolder.questionImage.setLayoutParams(params);

				LinearLayout.LayoutParams paramstwo = new LinearLayout.LayoutParams(
						100, LayoutParams.WRAP_CONTENT, 2);

				viewHolder.question.setLayoutParams(paramstwo);

				Log.e("in the if block :D ", questionImage);

			}

			else {
				viewHolder.questionImage.setVisibility(View.VISIBLE);
				AQuery aq = new AQuery(convertView);
				aq.id(viewHolder.questionImage).image(
						arrayList.get(position).getQuestion_image());

				Log.e("in the else block :D ", arrayList.get(position)
						.getQuestion_image());
			}

			Log.e("salik", "abc " + arrayList.get(position).getQuestion_image());

			if (arrayList.get(position).getAnswer_image().equals(null)) {
				Log.e("here", arrayList.get(position).getAnswer_image());

				viewHolder.answerimg_present.setVisibility(View.INVISIBLE);
				// Hide ImageView
				// Show Txt Answer
				viewHolder.Answer.setVisibility(View.VISIBLE);

			}

			else if (!answerDescription.equals(null)) {
				// Show ImageView
				// Hide Txt Answer
				Log.e("image", arrayList.get(position).getAnswer_image());
				Log.e("in the else block :D ", answerDescription);
				viewHolder.Answer.setVisibility(View.VISIBLE);
				viewHolder.answerimg_present.setVisibility(View.INVISIBLE);
				AQuery aq = new AQuery(convertView);
				aq.id(viewHolder.answerimg_present).image(
						arrayList.get(position).getAnswer_image());

				// set url image here
			}

			return row;
		}

		class ViewHolder {
			TextView question;
			TextView Answer;
			ImageView questionImage;

			ImageView correct_wrong;
			ImageView answerimg_present; // answer is in the form of image
		}
	}

}// MainActivityResult
