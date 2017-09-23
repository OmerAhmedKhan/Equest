package com.equest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.constants.constants;
import com.equest.network.URLs;

public class finishScreen extends Activity {
	TextView timerOnFinish;
	Intent onFinishTimer;
	Bundle message;
	String messageFromQuestionActivity;
	List<NameValuePair> serviceCall;
	Button finish;
	Context context;
	String[] questionsIds, answeredIds, isCorrectIds;
	String StrQuestionsIds, StrAnsweredIds, StrIsCorrectIds;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finis_screen);
		constants.isFinishedOrTimeUp=true;
		serviceCall = new ArrayList<NameValuePair>();
		context = this;

		if (constants.isLogOut) {
			questionsIds = new String[constants.index];
			answeredIds = new String[constants.index];
			isCorrectIds = new String[constants.index];
		}

		if (!constants.isFinished && constants.isLogOut == false) {
			onFinishTimer = this.getIntent();
			message = onFinishTimer.getExtras();
			messageFromQuestionActivity = (String) message
					.get("timer_finished");
			timerOnFinish = (TextView) findViewById(R.id.tv_instructions_title);
			timerOnFinish.setText(messageFromQuestionActivity);
		}
		/* else if(constants.isLogOut) */
		

		finish = (Button) findViewById(R.id.btn_finish_test);

		finish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// omer do your work here :P
				// omer work is done
				timer.destroyTimerInstance();//destroy timer 

				if (constants.isLogOut) {
					int i = 0;
					while (constants.question_attempted_id[i] != null) {
						questionsIds[i] = constants.question_attempted_id[i];
						answeredIds[i] = constants.answer_attempted_id[i];
						isCorrectIds[i] = constants.is_correct_wrt_answer_id[i];

						i++;
					}

					/*
					 * Log.e("getQuestion", Arrays.toString(questionsIds));
					 * Log.e("getAnswer", Arrays.toString(answeredIds));
					 * Log.e("getIsCorrecrt", Arrays.toString(isCorrectIds));
					 */

					StrQuestionsIds = Arrays.toString(questionsIds);
					StrAnsweredIds = Arrays.toString(answeredIds);
					StrIsCorrectIds = Arrays.toString(isCorrectIds);
					

					serviceCall.add(new BasicNameValuePair("functionName",
							"receiveAnswers"));
					serviceCall.add(new BasicNameValuePair("user_id",
							constants.user_id));
					serviceCall.add(new BasicNameValuePair("question_id",
							StrQuestionsIds));
					serviceCall.add(new BasicNameValuePair("answer_given_id",
							StrAnsweredIds));
					serviceCall.add(new BasicNameValuePair("is_correct",
							StrIsCorrectIds));

				
				}

				else

				{

					StrQuestionsIds = Arrays
							.toString(constants.question_attempted_id);
					StrAnsweredIds = Arrays
							.toString(constants.answer_attempted_id);
					StrIsCorrectIds = Arrays
							.toString(constants.is_correct_wrt_answer_id);
					
					
					StrQuestionsIds=StrQuestionsIds.replace("[", "");
					StrAnsweredIds=StrAnsweredIds.replace("[", "");
					StrIsCorrectIds=StrIsCorrectIds.replace("[", "");
					StrQuestionsIds=StrQuestionsIds.replace("]", "");
					StrAnsweredIds=StrAnsweredIds.replace("]", "");
					StrIsCorrectIds=StrIsCorrectIds.replace("]", "");
					
					
					serviceCall.add(new BasicNameValuePair("functionName",
							"receiveAnswers"));
					serviceCall.add(new BasicNameValuePair("user_id",
							constants.user_id));
					serviceCall.add(new BasicNameValuePair("question_id",
							StrQuestionsIds));
					serviceCall.add(new BasicNameValuePair("answer_given_id",
							StrAnsweredIds));
					serviceCall.add(new BasicNameValuePair("is_correct",
							StrIsCorrectIds));
	
					

				}
				try {
					new reportTestResult().execute().get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//Log.e("answerArray", StrAnsweredIds);
			//Log.e("questionArray", StrQuestionsIds);
			//Log.e("iscorrectArray", StrIsCorrectIds);
				Intent goToLogin=new Intent(finishScreen.this,Login.class);
				startActivity(goToLogin);
				finish();

			}
		}); // onclick listener ends

	} // oncreate ends

	private class reportTestResult extends AsyncTask<Void, Void, Void> {
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
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			result = Network.makeHttpRequest(context, URLs.Base_URL, "POST",
					serviceCall);
			Log.e("JSON", result);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

}
