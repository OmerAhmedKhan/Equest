package com.equest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class OfflineResults extends Activity {

	TextView TotalScoretvA, TotalEasytvA, TotalMediuntvA, TotalHardtvA;
	TextView TotalScoretvB, TotalEasytvB, TotalMediuntvB, TotalHardtvB;
	TextView TabletTotalScoretvA, TabletTotalEasytvA, TabletTotalMediuntvA, TabletTotalHardtvA;
	TextView TabletTotalScoretvB, TabletTotalEasytvB, TabletTotalMediuntvB, TabletTotalHardtvB;
	LinearLayout TableLayout;
	ScrollView PotraitScroll;
	String userid;
	String resultTableColunms[] = new String[11];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.offline_results);
		//POTRAIT//
		
		PotraitScroll=(ScrollView) findViewById(R.id.scroll_offline_result);
		
		TotalScoretvA = (TextView) findViewById(R.id.tv_offline_result_firstpointA);
		TotalEasytvA = (TextView) findViewById(R.id.tv_offline_result_secondpointA);
		TotalMediuntvA = (TextView) findViewById(R.id.tv_offline_result_thirdpointA);
		TotalHardtvA = (TextView) findViewById(R.id.tv_offline_result_fourthpointA);

		TotalScoretvB = (TextView) findViewById(R.id.tv_offline_result_firstpointB);
		TotalEasytvB = (TextView) findViewById(R.id.tv_offline_result_secondpointB);
		TotalMediuntvB = (TextView) findViewById(R.id.tv_offline_result_thirdpointB);
		TotalHardtvB = (TextView) findViewById(R.id.tv_offline_result_fourthpointB);
		// TotalHardtv=(TextView)
		// findViewById(R.id.tv_offline_result_fifthpoint);

		//LANDSCAPE FOR TABLET
		
		TableLayout=(LinearLayout) findViewById(R.id.tablet_scroll_linear_offline_result);
		
		TabletTotalScoretvA = (TextView) findViewById(R.id.tablet_tv_offline_result_firstpointA);
		TabletTotalEasytvA = (TextView) findViewById(R.id.tablet_tv_offline_result_secondpointA);
		TabletTotalMediuntvA = (TextView) findViewById(R.id.tablet_tv_offline_result_thirdpointA);
		TabletTotalHardtvA = (TextView) findViewById(R.id.tablet_tv_offline_result_fourthpointA);

		TabletTotalScoretvB = (TextView) findViewById(R.id.tablet_tv_offline_result_firstpointB);
		TabletTotalEasytvB = (TextView) findViewById(R.id.tablet_tv_offline_result_secondpointB);
		TabletTotalMediuntvB = (TextView) findViewById(R.id.tablet_tv_offline_result_thirdpointB);
		TabletTotalHardtvB = (TextView) findViewById(R.id.tablet_tv_offline_result_fourthpointB);
		
		
		Intent intent = getIntent();
		userid = intent.getExtras().getString("userId");

		resultTableColunms = EquestDB.getResultByUserID(OfflineResults.this,
				userid);

		TotalScoretvA.setText(resultTableColunms[2]);
		TotalScoretvB.setText("/" + resultTableColunms[1]);
		TotalEasytvA.setText(resultTableColunms[7]);
		TotalEasytvB.setText("/" + resultTableColunms[4]);
		TotalMediuntvA.setText(resultTableColunms[8]);
		TotalMediuntvB.setText("/" + resultTableColunms[5]);
		TotalHardtvA.setText(resultTableColunms[9]);
		TotalHardtvB.setText("/" + resultTableColunms[6]);
		
		TabletTotalScoretvA.setText(resultTableColunms[2]);
		TabletTotalScoretvB.setText("/" + resultTableColunms[1]);
		TabletTotalEasytvA.setText(resultTableColunms[7]);
		TabletTotalEasytvB.setText("/" + resultTableColunms[4]);
		TabletTotalMediuntvA.setText(resultTableColunms[8]);
		TabletTotalMediuntvB.setText("/" + resultTableColunms[5]);
		TabletTotalHardtvA.setText(resultTableColunms[9]);
		TabletTotalHardtvB.setText("/" + resultTableColunms[6]);
		
	}
	
	 // Check screen orientation or screen rotate event here
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        	if(isTablet(OfflineResults.this)){
        		//Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        		PotraitScroll.setVisibility(View.GONE);
        		TableLayout.setVisibility(View.VISIBLE);
        		
        	}
        	else{
        		//Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
        	}
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        	PotraitScroll.setVisibility(View.VISIBLE);
    		TableLayout.setVisibility(View.GONE);
        }
        
    }
    
    public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
