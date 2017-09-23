package com.equest;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends IntentService {
	
	
	
	Handler handler;

	
	
	// Just a constructor
	public NotificationService() {
		super("NotificationService");
	}

	
	// Handles the intent when service is called
	@Override
	protected void onHandleIntent(Intent intent) {
		
		
		
		// Gets the text to be displayed as a notification
		handler = new Handler();
		
		
//			handler.postDelayed(new Runnable() {
//			    @Override
//			    public void run() {
//			        // Do something after 5s = 5000ms
//			    	new getNotification_async().execute();
//			    }
//			}, 8640);
			
			
			// Code to create a standard android notification (named builder)
			// This particular code requires an API >= 11
			NotificationCompat.Builder builder =
					// Sets context
		            new NotificationCompat.Builder(this)
					// Sets an image on the left side of the notification display
		                    .setSmallIcon(android.R.drawable.stat_notify_chat)
		            // Sets the title of the notification
		            // I've set it to the application name
		                    .setContentTitle("Udated!!!")
		            // the notification text to be shown
//		                    .setContentText("Test of "+subject+" will be held on "+date+" at "+time)
		                    .setContentText("Results for offline Results are updated")
		            // Cancels the notification when I click it
		                    .setAutoCancel(true);
			
			// Just a Random Notification ID
		    int NOTIFICATION_ID = 12345;

		    // Sets the target Activity which should open when I click on the notification
		    Intent targetIntent = new Intent(this, DataFetchService.class);
		    
		    // A pending intent is a requirement for notifications
		    // No particular changes requires for this line of code
		    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		    builder.setContentIntent(contentIntent);
		    
		    // Calls the notification service to notify
		    NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		    nManager.notify(NOTIFICATION_ID, builder.build());
				
			
		
		
		/*Log.e("TEXT", notifcation.toString());			//Chalta wa code
		if(notifcation.contentEquals("abc"))
		{
			Log.e("IN", "in");
		// Code to create a standard android notification (named builder)
		// This particular code requires an API >= 11
		NotificationCompat.Builder builder =
				// Sets context
	            new NotificationCompat.Builder(this)
				// Sets an image on the left side of the notification display
	                    .setSmallIcon(android.R.drawable.stat_notify_chat)
	            // Sets the title of the notification
	            // I've set it to the application name
	                    .setContentTitle("Service Notification")
	            // the notification text to be shown
	                    .setContentText(notifcation)
	            // Cancels the notification when I click it
	                    .setAutoCancel(true);
		
		// Just a Random Notification ID
	    int NOTIFICATION_ID = 12345;

	    // Sets the target Activity which should open when I click on the notification
	    Intent targetIntent = new Intent(this, MainActivity.class);
	    
	    // A pending intent is a requirement for notifications
	    // No particular changes requires for this line of code
	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    builder.setContentIntent(contentIntent);
	    
	    // Calls the notification service to notify
	    NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    nManager.notify(NOTIFICATION_ID, builder.build());
		}*/
		
	}

}
