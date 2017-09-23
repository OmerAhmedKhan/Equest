package com.equest;

import java.util.Arrays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.constants.constants;

public class BaseActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.act);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			// go to login page

			/****************/
			if (constants.roleId == 1) {

				/**************************************/
				final Dialog dialog = new Dialog(this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog_box);
				// dialog.setTitle("Title...");
				TextView text = (TextView) dialog.findViewById(R.id.text);

				text.setText("Are your really want to exit the test?");
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.logo_dialogbox);

				TextView dialogtextviewone = (TextView) dialog
						.findViewById(R.id.dialogButtonResults);
				TextView dialogtextviewtwo = (TextView) dialog
						.findViewById(R.id.dialogButtonTestReview);

				dialogtextviewone.setText("No");
				dialogtextviewtwo.setText("Yes");

				dialogtextviewtwo
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {

								constants.isLogOut = true;
								setContentView(R.layout.finis_screen);
								constants.isFinishedOrTimeUp = true;
								timer timerOfTest = timer.getInstance();
								timerOfTest.stopTimer();
								timer.destroyTimerInstance();
								Intent ourIntent = new Intent(
										BaseActivity.this, finishScreen.class);
								startActivity(ourIntent);
								dialog.cancel();
								finish();

							}// onClick

						});

				dialogtextviewone
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {

								
								dialog.cancel();
							}// onClick

						});

				dialog.show();

				/****************************************/

				Log.e("getQuestion",
						Arrays.toString(constants.question_attempted_id));
				Log.e("getAnswer",
						Arrays.toString(constants.answer_attempted_id));
				Log.e("getIsCorrecrt",
						Arrays.toString(constants.answer_attempted_id));
				
			} else {
				Intent ourIntent = new Intent(BaseActivity.this, Login.class);
				startActivity(ourIntent);
				finish();
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}