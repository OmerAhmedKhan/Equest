package com.equest.utilities;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class timer {

	long  timeInSeconds;
	long  timeInterval;
	int seconds;
	int hours;
	int minutes;
	boolean isFinished;
	CountDownTimer timer;
	TextView txtView;
	
	public timer(TextView txtView){
		isFinished = true;
		this.txtView = txtView;
	}
	public void setTimer(long milliSeconds,long interval)
	{
		isFinished=true;
		timer=new CountDownTimer(milliSeconds,interval) {
			
			@Override
			public void onTick(long milliSeconds) {
				Log.i("Salik", "" + milliSeconds);
				 seconds = (int) (milliSeconds / 1000) % 60 ;
				 minutes = (int) ((milliSeconds / (1000*60)) % 60);
				 hours   = (int) ((milliSeconds / (1000*60*60)) % 24);
				 txtView.setText("HOURS:"+hours+"MINUTES:"+minutes+"SECONDS:"+seconds);
			}
			
			@Override
			public void onFinish() {
				Log.i("Salik", "Finished" );
				isFinished=false;
				
			}
		};
	}
	public void startTimer()
	{
		timer.start();
	}
	public CountDownTimer getTimer()
	{
		return timer;
	}
	public boolean retIsFinished()
	{
		return isFinished;
	}
	public int retHours()
	{
		return hours;
	}
	public int retMinutes()
	{
		return minutes;
	}
	public int retSeconds()
	{
		return seconds;
	}


}
