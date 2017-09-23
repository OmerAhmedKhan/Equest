package com.equest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustTextView extends TextView {
	
	public CustTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontManager.setFontFromAttributeSet(context, attrs, this,R.styleable.CustTextView,R.styleable.CustTextView_cust_font);
	}
}
