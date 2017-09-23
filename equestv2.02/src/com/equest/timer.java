package com.equest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;

public class timer {
	// timer class

	long timeInSeconds;
	long timeInterval;
	int seconds;
	int hours;
	int minutes;
	boolean isFinished;
	CountDownTimer timer;
	TextView txtViewMins;
	TextView txtViewSecs;
	Context context;

	static timer instance = null;
	
	private timer(Context context, TextView txtViewMinutes,
			TextView txtViewSeconds) {
		isFinished = true;
		this.txtViewMins = txtViewMinutes;
		this.txtViewSecs = txtViewSeconds;
		this.context = context;
	}
	private timer() {
	}
	
	public static timer getInstance(Context context, TextView txtViewMinutes,
			TextView txtViewSeconds) {
		if(instance == null)
			instance = new timer(context, txtViewMinutes,
					txtViewSeconds);
		return instance;
	}
	public static timer getInstance() {
		if(instance == null)
			instance = new timer();
		return instance;
	}
	public static void destroyTimerInstance()
	{
		instance=null;
	}

	// set timer's timer and interval to decrement by
	public void setTimer(long milliSeconds, long interval) {
		isFinished = true;
		timeInSeconds=milliSeconds;
		timer = new CountDownTimer(milliSeconds, interval) {
			// method that sets minutes and seconds at every tick
			@Override
			public void onTick(long milliSeconds) {
				
				seconds = (int) (milliSeconds / 1000) % 60;
				minutes = (int) ((milliSeconds / (1000 * 60)) % 60);
				hours = (int) ((milliSeconds / (1000 * 60 * 60)) % 24);
				String mins, secs, hrs;
				hrs = String.valueOf(hours);
				mins = String.valueOf(minutes);
				secs = String.valueOf(seconds);
				if (hours < 10) {
					hrs = "0" + String.valueOf(hours);
				}
				if (minutes < 10) {
					mins = "0" + String.valueOf(minutes);
				}
				if (seconds < 10) {
					secs = "0" + String.valueOf(seconds);
				}
				if (hours == 0) {
					txtViewMins.setText(mins + ":");
					txtViewSecs.setText(secs);
				} else {
					txtViewMins.setText(mins + ":");
					txtViewSecs.setText(secs);
				}
			}

			// method to go to the finish screen
			@Override
			public void onFinish() {
				// Log.i("Salik", "Finished" );
				isFinished = false;
				Intent goToFinishScreen = new Intent(context,
						finishScreen.class);
			
				goToFinishScreen.putExtra("timer_finished", "Time's Up!");
				context.startActivity(goToFinishScreen);
				((Activity) context).finish();

			}
		};
	}

	// start timer
	public void startTimer() {
		timer.start();
	}

	// stop timer
	public void stopTimer() {
		timer.cancel();
	}

	// return timer object
	public CountDownTimer getTimer() {
		return timer;
	}

	// return hours
	public int retHours() {
		return hours;
	}

	// return minutes
	public int retMinutes() {
		return minutes;
	}

	// return seconds
	public int retSeconds() {
		return seconds;
	}
	
	

}
