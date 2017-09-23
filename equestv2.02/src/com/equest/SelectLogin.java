package com.equest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.constants.constants;
import com.equest.utilities.InternetCheckerMainActivity;

public class SelectLogin extends Activity {
	
	Button loginOnline,loginOffline;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login_selection);
		
		loginOnline=(Button) findViewById(R.id.btn_login_online);
		loginOffline=(Button) findViewById(R.id.btn_login_offline);
		
		loginOnline.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				constants.isLogOffline=false;
				Intent intent = new Intent(SelectLogin.this, InternetCheckerMainActivity.class);
				startActivity(intent);
				
			}
		});
		
		loginOffline.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				constants.isLogOffline=true;
				Intent intent = new Intent(SelectLogin.this, showAllUsers.class);
				startActivity(intent);
				
				
			}
		});
		
	}
	
	
	
}
