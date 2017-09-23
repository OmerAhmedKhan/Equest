package com.equest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.constants.constants;
import com.equest.network.URLs;

public class Questions_Activity extends BaseActivity implements OnClickListener {

	private Context context;

	// json definitions
	JSONObject JSON;
	JSONObject header;
	JSONObject Questions, question_no;
	JSONObject body;
	JSONObject Answers, answer_no, answer_detail;

	// list and arrays list for json parcing storage
	List<NameValuePair> POST = new ArrayList<NameValuePair>();
	ArrayList<QuestionAnswers> QuestionAnswersPairs;

	// definition of timer class
	timer timerOfTest;

	// Aquery for image loading
	AQuery aq;

	int questionCount = 0;
	int[] answeredOptionBtnIndex;

	// json for service store in it
	String pairQA;
	String jsonCode;

	// String test = "";

	String[] answersIds;
	String[] questionIds;
	String[] isCorectWrtQuestion;

	// miscleaneous booleean for error checks
	boolean isAnswerCheck = false;
	boolean isAnswerImageCheck = false;
	boolean[] isPrevoiusAnswerImage;
	boolean[] isAnsweredArray;
	boolean previousButtonCheckForNextButton;

	// timer views
	TextView testTimerMinutes;
	TextView testTimerSeconds;

	// Question Text layout views
	ScrollView textview;
	TextView question, questionNo;

	Button buttonsAnswer[];

	ImageView questionImage;
	ImageButton btnTextNext;
	ImageButton btnTextPrev;

	// Question Image layout views ///////////////////////////////////////
	ScrollView imageview;
	TextView questionImgDescription;
	ImageView questionImgImages;
	ImageButton[] answerBtnImages;
	ImageButton btnImgNext, btnImgPrev;

	/***************************************************************************/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_layout_text);

		constants.index = 0;

		// Initializing timer layout views
		testTimerMinutes = (TextView) findViewById(R.id.tv_question_text_timer_mins);
		testTimerSeconds = (TextView) findViewById(R.id.tv_question_text_timer_secs);

		// Initializing layout views
		imageInitializer();
		textInitializer();

		// invisible both questions layout
		textview.setVisibility(View.GONE);
		imageview.setVisibility(View.GONE);

		// initiallizing most important arraylist
		QuestionAnswersPairs = new ArrayList<QuestionAnswers>();
		context = this;

		// sending post method name to the api
		POST.add(new BasicNameValuePair("functionName", "question_pairs"));
		constants.isFinished = false;

		// get json and parse it in Asyn class

		try {
			new displayQuestionAnswers().execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}// Async in backgrounnd end

		// initiallizing Aquesu

		aq = new AQuery(this);

		// start timer

		timerOfTest = timer.getInstance(this, testTimerMinutes,
				testTimerSeconds);
		timerOfTest.setTimer(QuestionAnswers.retTimeOfTest(), 1000);
		Log.e("test time", "" + QuestionAnswers.retTimeOfTest());

		timerOfTest.startTimer();

		// Initiallizing arrays used for indexing and controlling questions
		// display
		arraysInitializer();

		// filling isAnswerArray for next button check for already given answer

		isAnsweredArray = new boolean[questionCount];

		for (int fill = 0; fill < isAnsweredArray.length; fill++) {
			isAnsweredArray[fill] = false;
		}

		// Initializing constant terms used for insertion attempted answers

		constants.question_attempted_id = new String[questionCount];
		constants.answer_attempted_id = new String[questionCount];
		constants.is_correct_wrt_answer_id = new String[questionCount];

		// checking if there is any images in the answer of the respected
		// question

		// get question and answer from json and display it on the respective
		// layout
		getAndDisplayQuestions();

		// btnTextNext.setOnClickListener(this);
		// btnTextPrev.setOnClickListener(this);
		buttonsAnswer[0].setOnClickListener(this);
		buttonsAnswer[1].setOnClickListener(this);
		buttonsAnswer[2].setOnClickListener(this);
		buttonsAnswer[3].setOnClickListener(this);

		btnImgPrev.setVisibility(View.GONE);
		// btnTextPrev.setVisibility(View.GONE);

	}// end Oncreate

	/**********************************************************************************/

	@Override
	public void onClick(View v) {

		// switch case of answer option button and for next and previous buttons

		switch (v.getId()) {

		// Answer option button no 1 backend coding

		case R.id.img_answer_1_img_description:
		case R.id.btn_answer_option_1: {

			isAnswerCheck = true;
			isAnsweredArray[constants.index] = true;

			// if answer layout appear set Answer image layout button vies
			if (isPrevoiusAnswerImage[constants.index]) {
				v.setBackgroundResource(R.drawable.btn_options_placeholder_pressed);
				answerBtnImages[1]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[2]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[3]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);

			} else {
				v.setBackgroundResource(R.drawable.btn_blue_pressed);
				buttonsAnswer[1]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[2]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[3]
						.setBackgroundResource(R.drawable.btn_blue_normal);
			}

			answeredOptionBtnIndex[constants.index] = 1;

			// saving it into array which is then to pass to DB after
			// finish,logout or timeout

			constants.answer_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retAnswerId(0);

			constants.question_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retQuestionId();

			constants.is_correct_wrt_answer_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retIsCorrect(0);

			break;
		}

		// Answer option button no 2 backend coding
		case R.id.img_answer_2_img_description:
		case R.id.btn_answer_option_2: {
			isAnswerCheck = true;
			isAnsweredArray[constants.index] = true;

			// if answer layout appear set Answer image layout button vies
			if (isPrevoiusAnswerImage[constants.index]) {
				v.setBackgroundResource(R.drawable.btn_options_placeholder_pressed);
				answerBtnImages[0]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[2]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[3]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);

			} else {
				v.setBackgroundResource(R.drawable.btn_blue_pressed);
				buttonsAnswer[0]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[2]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[3]
						.setBackgroundResource(R.drawable.btn_blue_normal);
			}

			answeredOptionBtnIndex[constants.index] = 2;

			// saving it into array which is then to pass to DB after
			// finish,logout or timeout

			constants.answer_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retAnswerId(1);

			constants.question_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retQuestionId();
			constants.is_correct_wrt_answer_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retIsCorrect(1);

			break;
		}

		// Answer option button no 3 backend coding
		case R.id.img_answer_3_img_description:
		case R.id.btn_answer_option_3: {
			isAnswerCheck = true;
			isAnsweredArray[constants.index] = true;

			// if answer layout appear set Answer image layout button vies
			if (isPrevoiusAnswerImage[constants.index]) {
				v.setBackgroundResource(R.drawable.btn_options_placeholder_pressed);
				answerBtnImages[0]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[1]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[3]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);

			} else {
				v.setBackgroundResource(R.drawable.btn_blue_pressed);
				buttonsAnswer[0]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[1]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[3]
						.setBackgroundResource(R.drawable.btn_blue_normal);
			}

			answeredOptionBtnIndex[constants.index] = 3;

			// saving it into array which is then to pass to DB after
			// finish,logout or timeout

			constants.answer_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retAnswerId(2);

			constants.question_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retQuestionId();

			constants.is_correct_wrt_answer_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retIsCorrect(2);

			break;
		}

		// Answer option button no 4 backend coding
		case R.id.img_answer_4_img_description:
		case R.id.btn_answer_option_4: {
			isAnswerCheck = true;
			isAnsweredArray[constants.index] = true;

			// if answer layout appear set Answer image layout button vies

			if (isPrevoiusAnswerImage[constants.index]) {
				v.setBackgroundResource(R.drawable.btn_options_placeholder_pressed);
				answerBtnImages[1]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[2]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[0]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);

			} else {
				v.setBackgroundResource(R.drawable.btn_blue_pressed);
				buttonsAnswer[0]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[1]
						.setBackgroundResource(R.drawable.btn_blue_normal);
				buttonsAnswer[2]
						.setBackgroundResource(R.drawable.btn_blue_normal);
			}

			answeredOptionBtnIndex[constants.index] = 4;

			// saving it into array which is then to pass to DB after
			// finish,logout or timeout

			constants.answer_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retAnswerId(3);

			constants.question_attempted_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retQuestionId();

			constants.is_correct_wrt_answer_id[constants.index] = QuestionAnswersPairs
					.get(constants.index).retIsCorrect(3);

			break;
		}

		// Next button coding
		// case R.id.btn_next_question_text:
		case R.id.btn_next_question_img: {

			// visible previous button on first next
			btnImgPrev.setVisibility(View.VISIBLE);
			// btnTextPrev.setVisibility(View.VISIBLE);

			// set all text layout answer option to unchecked
			buttonsAnswer[0].setBackgroundResource(R.drawable.btn_blue_normal);
			buttonsAnswer[1].setBackgroundResource(R.drawable.btn_blue_normal);
			buttonsAnswer[2].setBackgroundResource(R.drawable.btn_blue_normal);
			buttonsAnswer[3].setBackgroundResource(R.drawable.btn_blue_normal);

			// set all img layout answer option to unchecked if there is any
			// image
			if (isPrevoiusAnswerImage[constants.index]) {

				answerBtnImages[1]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[2]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[3]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
				answerBtnImages[3]
						.setBackgroundResource(R.drawable.btn_options_placeholder_normal);

			} else {
				;
			}

			// if next is pressed on last question send to finish screen
			if ((constants.index == questionCount - 1) && isAnswerCheck) {

				timerOfTest.stopTimer();
				timer.destroyTimerInstance();
				Intent intent = new Intent(this, finishScreen.class);
				constants.isFinished = true;
				startActivity(intent);
				finish();

			}// end if

			// else if answer is given save it into a constant.attempted answer
			// index and prepare for next question
			else if ((isAnswerCheck) || (isAnsweredArray[constants.index])) {

				isAnswerCheck = false;
				isAnswerImageCheck = false;

				constants.index++;

				Log.e("Constant_index", "" + constants.index);
				Log.e("option", "" + answeredOptionBtnIndex[constants.index]);

				if (answeredOptionBtnIndex[constants.index] != 0) {
					buttonsAnswer[answeredOptionBtnIndex[constants.index] - 1]
							.setBackgroundResource(R.drawable.btn_blue_pressed);
				}

				// prepare for next question

				getAndDisplayQuestions();

			}// end else if

			// else if answer is not given prompt user to give some answer

			else {

				if ((constants.index) == 0) {
					btnImgPrev.setVisibility(View.GONE);
					// btnTextPrev.setVisibility(View.GONE);
				}

				Toast.makeText(getApplicationContext(),
						"There is no negative marking please give an answer",
						Toast.LENGTH_SHORT).show();
			}// end else

			break;

		}

		// previous button use to recheck the attempted answer or to change it

		// case R.id.btn_previous_question_text:
		case R.id.btn_previous_question_img: {

			Log.e("questionCount", String.valueOf(questionCount));

			if ((constants.index) == 1) {
				btnImgPrev.setVisibility(View.GONE);
				// btnTextPrev.setVisibility(View.GONE);
			}

			// if first question of a test is invisible previous button

			if (constants.index == 0) {

				btnImgPrev.setVisibility(View.GONE);
				// btnTextPrev.setVisibility(View.GONE);

			} // end main if

			// else show previous button
			else {

				previousButtonCheckForNextButton = true;

				constants.index--;

				// if imaged question then load image layout else text layout

				if (isPrevoiusAnswerImage[constants.index]) {
					textview.setVisibility(View.GONE);
					imageview.setVisibility(View.VISIBLE);

					questionNo.setText("QUESTION " + (constants.index + 1)
							+ "/" + questionCount);

					questionImgDescription.setText((Html.fromHtml(getString(
							R.string.ques,
							QuestionAnswersPairs.get(constants.index)
									.retQuestion()))));
					// question_img_images

					// to load all answer option images

					

					answerBtnImages[0]
							.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
					answerBtnImages[1]
							.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
					answerBtnImages[2]
							.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
					answerBtnImages[3]
							.setBackgroundResource(R.drawable.btn_options_placeholder_normal);
					answerBtnImages[answeredOptionBtnIndex[constants.index] - 1]
							.setBackgroundResource(R.drawable.btn_options_placeholder_pressed);

					aq.id(R.id.img_answer_1_img_description).image(
							QuestionAnswersPairs.get(constants.index)
									.retAnswerImage(0));
					aq.id(R.id.img_answer_2_img_description).image(
							QuestionAnswersPairs.get(constants.index)
									.retAnswerImage(1));
					aq.id(R.id.img_answer_3_img_description).image(
							QuestionAnswersPairs.get(constants.index)
									.retAnswerImage(2));
					aq.id(R.id.img_answer_4_img_description).image(
							QuestionAnswersPairs.get(constants.index)
									.retAnswerImage(3));

				} else {

					textview.setVisibility(View.VISIBLE);
					imageview.setVisibility(View.GONE);

					question.setText((Html.fromHtml(getString(R.string.ques,
							QuestionAnswersPairs.get(constants.index)
									.retQuestion()))));

					if (QuestionAnswersPairs.get(constants.index)
							.retIsImageQuestion().equals("1")) {

						aq.id(R.id.img_question_text_description).image(
								QuestionAnswersPairs.get(constants.index)
										.retQuestionImage());

						questionImage.setVisibility(View.VISIBLE);
					} else {
						questionImage.setVisibility(View.GONE);
					}

					questionNo.setText("QUESTION " + (constants.index + 1)
							+ "/" + questionCount);

					

					buttonsAnswer[0]
							.setBackgroundResource(R.drawable.btn_blue_normal);
					buttonsAnswer[1]
							.setBackgroundResource(R.drawable.btn_blue_normal);
					buttonsAnswer[2]
							.setBackgroundResource(R.drawable.btn_blue_normal);
					buttonsAnswer[3]
							.setBackgroundResource(R.drawable.btn_blue_normal);

//					Log.e("answered_option_btn_index_phlye",
//							String.valueOf(answeredOptionBtnIndex[constants.index] - 1));
					buttonsAnswer[answeredOptionBtnIndex[constants.index] - 1]
							.setBackgroundResource(R.drawable.btn_blue_pressed);
//					Log.e("answered_option_btn_index bad m",
//							String.valueOf(answeredOptionBtnIndex[constants.index] - 1));

					// get all answers option in while loop

					int i = 0;
					while (i < QuestionAnswersPairs.get(constants.index)
							.retCountAnswerOption()) {
						// answers[i]=QuestionAnswersPairs.get(constants.index).retAnswer(i);
						buttonsAnswer[i].setText(QuestionAnswersPairs.get(
								constants.index).retAnswer(i));

						i++;
					}
				}
			}// end main else

			break;

		}

		default:
			break;
		}

	}// end on click listener

	/*************************************************************************************/

	// function for initializing question image layout
	// views/////////////////////////////

	public void imageInitializer() {
		questionImgDescription = (TextView) findViewById(R.id.tv_question_img_description);
		questionImgImages = (ImageView) findViewById(R.id.img_question_img_description);
		answerBtnImages = new ImageButton[4];
		answerBtnImages[0] = (ImageButton) findViewById(R.id.img_answer_1_img_description);
		answerBtnImages[1] = (ImageButton) findViewById(R.id.img_answer_2_img_description);
		answerBtnImages[2] = (ImageButton) findViewById(R.id.img_answer_3_img_description);
		answerBtnImages[3] = (ImageButton) findViewById(R.id.img_answer_4_img_description);
		btnImgNext = (ImageButton) findViewById(R.id.btn_next_question_img);
		btnImgPrev = (ImageButton) findViewById(R.id.btn_previous_question_img);

		btnImgNext.setOnClickListener(this);
		btnImgPrev.setOnClickListener(this);
		answerBtnImages[0].setOnClickListener(this);
		answerBtnImages[1].setOnClickListener(this);
		answerBtnImages[2].setOnClickListener(this);
		answerBtnImages[3].setOnClickListener(this);
	}

	/*************************************************************************************/
	// function for initializing question text layout
	// views/////////////////////////////

	public void textInitializer() {

		question = (TextView) findViewById(R.id.tv_question_text_description);
		questionNo = (TextView) findViewById(R.id.question_text_title);

		buttonsAnswer = new Button[4];
		buttonsAnswer[0] = (Button) findViewById(R.id.btn_answer_option_1);
		buttonsAnswer[1] = (Button) findViewById(R.id.btn_answer_option_2);
		buttonsAnswer[2] = (Button) findViewById(R.id.btn_answer_option_3);
		buttonsAnswer[3] = (Button) findViewById(R.id.btn_answer_option_4);

		questionImage = (ImageView) findViewById(R.id.img_question_text_description);
		imageview = (ScrollView) findViewById(R.id.scroll_questions_image);

		// btnTextNext = (ImageButton)
		// findViewById(R.id.btn_next_question_text);
		// btnTextPrev = (ImageButton)
		// findViewById(R.id.btn_previous_question_text);

		textview = (ScrollView) findViewById(R.id.scroll_questions_text);

	}

	/*************************************************************************************/
	// function for initializing arrays use throughout the
	// program////////////////////////////
	public void arraysInitializer() {
		answersIds = new String[questionCount];
		questionIds = new String[questionCount];
		answeredOptionBtnIndex = new int[questionCount];
		isCorectWrtQuestion = new String[questionCount];
		isPrevoiusAnswerImage = new boolean[questionCount];
		// previousButtonCheckForNextButton = new boolean[questionCount];
	}

	/*************************************************************************************/
	// function for get and display the questions index wise
	public void getAndDisplayQuestions() {

		int j = 0;

		while (j < QuestionAnswersPairs.get(constants.index)
				.retCountAnswerOption()) {
			// answers[i]=QuestionAnswersPairs.get(constants.index).retAnswer(i);
			if (!QuestionAnswersPairs.get(constants.index).retAnswerImage(j)
					.equals("0")) {
				isAnswerImageCheck = true;
			}

			j++;
		}

		isPrevoiusAnswerImage[constants.index] = isAnswerImageCheck;

		if (isAnswerImageCheck) {

			textview.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);

			questionImgDescription.setText((Html.fromHtml(getString(
					R.string.ques, QuestionAnswersPairs.get(constants.index)
							.retQuestion()))));

			questionNo.setText("QUESTION " + (constants.index + 1) + "/"
					+ questionCount);

			if (QuestionAnswersPairs.get(constants.index).retIsImageQuestion()
					.equals("1")) {

				aq.id(R.id.img_question_img_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retQuestionImage());

				questionImgImages.setVisibility(View.VISIBLE);
			} else {
				questionImgImages.setVisibility(View.GONE);
			}

			// question_img_images
//			Log.e("image_url", QuestionAnswersPairs.get(constants.index)
//					.retAnswerImage(0));
			// for(int i=0;i<4;i++)
			{
				aq.id(R.id.img_answer_1_img_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retAnswerImage(0));
				aq.id(R.id.img_answer_2_img_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retAnswerImage(1));
				aq.id(R.id.img_answer_3_img_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retAnswerImage(2));
				aq.id(R.id.img_answer_4_img_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retAnswerImage(3));
			}

			/*
			 * AQuery aq = new AQuery(convertView);
			 * aq.id(viewHolder.answerimg_present).image(arrayList.get(position)
			 * .getAnswer_image(), true, true, 0, R.drawable.ic_launcher);
			 */

		} else {

			textview.setVisibility(View.VISIBLE);
			imageview.setVisibility(View.GONE);

			//Log.e("index", String.valueOf(constants.index));

			// lat.setText((Html.fromHtml(getString(R.string.quiz,coord.getString("lat")))));

			question.setText((Html.fromHtml(getString(R.string.ques,
					QuestionAnswersPairs.get(constants.index).retQuestion()))));

			questionNo.setText("QUESTION " + (constants.index + 1) + "/"
					+ questionCount);

			Log.e("is_image",
					QuestionAnswersPairs.get(constants.index)
							.retIsImageQuestion()
							+ "|"
							+ QuestionAnswersPairs.get(constants.index)
									.retQuestionImage());

			if (QuestionAnswersPairs.get(constants.index).retIsImageQuestion()
					.equals("1")) {

				aq.id(R.id.img_question_text_description).image(
						QuestionAnswersPairs.get(constants.index)
								.retQuestionImage());

				questionImage.setVisibility(View.VISIBLE);
			} else {
				questionImage.setVisibility(View.GONE);
			}

			int i = 0;
			while (i < QuestionAnswersPairs.get(constants.index)
					.retCountAnswerOption()) {
				// answers[i]=QuestionAnswersPairs.get(constants.index).retAnswer(i);
				buttonsAnswer[i].setText(QuestionAnswersPairs.get(
						constants.index).retAnswer(i));

				i++;
			}
			// question.setText(test);
			// Log.e("test", test);
		}
	}

	/*************************************************************************************/
	// Asyn class for retrieving json from web services

	private class displayQuestionAnswers extends
			AsyncTask<Void, Void, JSONObject> {
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
		protected JSONObject doInBackground(Void... params) {

			try {
				pairQA = Network.makeHttpRequest(context, URLs.Base_URL,
						"POST", POST);
				//Log.e("json", "" + pairQA + "\n");
				JSON = new JSONObject(pairQA);
				header = JSON.getJSONObject("header");
				jsonCode = header.getString("code");
//				Log.e("CODE", "" + jsonCode + "\n");
//				Log.e("json", pairQA);
				body = JSON.getJSONObject("body");
				int count = Integer.parseInt(body.getString("total_question"));
				questionCount = count;

				//Log.e("count", String.valueOf(count));
				String isImageAnswer;
				Questions = body.getJSONObject("questions");
				QuestionAnswers.setTimeOfTest(body.getString(("time")));

				for (int i = 0; i <= count; i++) {

					question_no = Questions
							.getJSONObject(String.valueOf(i + 1));
					QuestionAnswers QA = new QuestionAnswers();
					QuestionAnswers.setTotalQuestions(count);
					QA.setQuestionId(question_no.getString("id"));
					QA.setQuestions(question_no.getString("question"),
							question_no.getString("is_image"));
					QA.setImageQuestion(question_no.getString("image"));
					Answers = question_no.getJSONObject("answers");
					answer_no = Answers.getJSONObject("answers");
					QuestionAnswers.setCountAnswerOptions(answer_no.length());
					//Log.e("options", "" + answer_no.length() + "\n");
					for (int j = 0; j < answer_no.length(); j++) {
						answer_detail = answer_no.getJSONObject(String
								.valueOf(j + 1));
//						Log.e("image answer",
//								"" + answer_detail.getString("image") + "\n");
						/*
						 * if(answer_detail.getString("image").equals("null")) {
						 * isImageAnswer="0"; } else { isImageAnswer="1"; }
						 * Log.e("isImageAnswer",""+isImageAnswer+"\n");
						 */
						QA.setAnswerId(answer_detail.getString("id"));
						QA.setAnswers(
								answer_detail.getString("option"),
								answer_detail.getString("is_correct"),
								answer_detail.getString("image").equals("null") ? "0"
										: answer_detail.getString("image"));
					}
					// answer_detail=answer_no.
					QuestionAnswersPairs.add(QA);
					//Log.e("QA", QuestionAnswersPairs.toString());

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return Questions;

		}

		@Override
		protected void onPostExecute(JSONObject Questions) {

			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			//Log.e("QuestionAnswersPairs", "" + QuestionAnswersPairs.toString());
			

			super.onPostExecute(Questions);

		}

	}

	/*************************************************************************************/

}
