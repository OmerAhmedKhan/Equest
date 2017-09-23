package com.equest.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connector {
	
private Context _context;
    
    //constructor 
	public Connector(Context context)
    {
        this._context = context;
    }
 
    public boolean isConnectingToInternet(){
    	
    	
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
		// CONNECTIVITY_SERVICE: retrieve a ConnectivityManager for handling
		// management of network connections.used with getSystemService
		
        // ConnectivityManager: is a Class that answers queries about the state
		// of network connectivity.
		// It also notifies applications when network connectivity changes.
        
		// Get an instance of this class by calling
		// Context.getSystemService(Context.CONNECTIVITY_SERVICE).
        
        
    if (connectivity != null)  //connection found  
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false; //not found
    }

}
