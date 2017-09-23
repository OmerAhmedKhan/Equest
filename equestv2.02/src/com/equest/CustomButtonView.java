package com.equest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButtonView extends Button{
	
	
	public CustomButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontManager.setFontFromAttributeSet(context, attrs, this,R.styleable.CustButtonView,R.styleable.CustButtonView_cust_btn_font);
	}
	
	

}
