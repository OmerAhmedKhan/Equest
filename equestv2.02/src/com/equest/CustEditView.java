package com.equest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustEditView extends EditText {

	public CustEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontManager.setFontFromAttributeSet(context, attrs, this,R.styleable.CustEditView,R.styleable.CustEditView_cust_edit_font);
	}
}// ends
