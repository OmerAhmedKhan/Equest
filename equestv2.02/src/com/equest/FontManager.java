package com.equest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontManager {
	// ** This enum is tightly coupled with the enum in res/values/attrs.xml! **
	// ** Make sure their orders stay the same **
	public enum Font {
		// CodeBold ("fonts/CODE Bold.otf"),
		// KraashBlack ("fonts/Kraash Black.ttf"),
		// NinaScript ("fonts/Nina script.ttf");

		OxygenBold("fonts/Oxygen_Bold.ttf"),
		// KraashBlack ("fonts/Kraash Black.ttf"),
		OxygenLight("fonts/Oxygen_Light.ttf"), HELVETICANEUELTPRO_HV(
				"fonts/HELVETICANEUELTPRO_HV.OTF"), HELVETICANEUELTPRO_TH(
				"fonts/HELVETICANEUELTPRO_TH.OTF"), HELVETICANEUELTPRO_THEX(
				"fonts/HELVETICANEUELTPRO_THEX.OTF"), OxygenRegular(
				"fonts/Oxygen_Regular.ttf");

		public final String fileName;

		private Font(String name) {
			fileName = name;
		}
	}

	public static void setFont(TextView tv, int fontId) {
		Font font = getFontFromId(fontId);
		Typeface typeface = Typeface.createFromAsset(tv.getContext()
				.getAssets(), font.fileName);
		tv.setTypeface(typeface);
	}

	public static Font getFontFromId(int fontId) {
		switch (fontId) {
		case 0:
			return Font.OxygenBold;
		case 1:
			return Font.OxygenLight;
		case 2:
			return Font.OxygenRegular;

		case 3:
			return Font.HELVETICANEUELTPRO_HV;
		case 4:
			return Font.HELVETICANEUELTPRO_TH;
		case 5:
			return Font.HELVETICANEUELTPRO_THEX;

		}

		return null;
	}

	public static void setFontFromAttributeSet(Context context,
			AttributeSet attrs, TextView tv, int[] id, int id1) {
		TypedArray a = context.obtainStyledAttributes(attrs, id);
		if (a.length() > 0) {
			int fontId = -1;
			for (int i = 0; i < a.length(); i++) {
				int attr = a.getIndex(i);
				if (attr == id1) {
					fontId = a.getInt(attr, -1);
				}
			}

			// Log.d(TAG, "FontId: " + fontId);
			if (fontId != -1) {
				FontManager.setFont(tv, fontId);
			}
		}
	}
}
