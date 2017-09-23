package com.equest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class EquestDB {

	// Insert new user into table.
	// Parameters are self explanatory
	// context = getapplicationcontext() // use as parameter in Android activity
	public static void insertUser(Context context, String userID, String fName,
			String lName, String email, String Pass) {
		ContentValues cv = new ContentValues();

		cv.put(EquestDbHelper.USERID, userID);
		cv.put(EquestDbHelper.FIRSTNAME, fName);
		cv.put(EquestDbHelper.LASTNAME, lName);
		cv.put(EquestDbHelper.EMAILID, email);
		cv.put(EquestDbHelper.PASSWORD, Pass);

		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.USER_TABLE);
		context.getContentResolver().insert(contentUri, cv);
	}

	// Gets user by ID
	// context = getapplicationcontext() // use as parameter in Android activity
	// Returns an Array of Strings Such that,
	// String[0]=UserTableID
	// String[1]=UserID
	// String[2]=FirstName
	// String[3]=LastnName
	// String[4]=EmailID
	// String[5]=Password
	public static String[] getUserByID(Context context, String userid) {
		String Columns[] = { 
				EquestDbHelper.ID_USER_TABLE,
				EquestDbHelper.USERID, 
				EquestDbHelper.FIRSTNAME,
				EquestDbHelper.LASTNAME, 
				EquestDbHelper.EMAILID,
				EquestDbHelper.PASSWORD };
		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.USER_TABLE);
		String selection = EquestDbHelper.USERID + " = " + userid;
		Cursor cursor = context.getContentResolver().query(contentUri, Columns,
				selection, null, null);
		if (cursor.moveToFirst()) {
			do {
				Columns[0] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.ID_USER_TABLE));
				Columns[1] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.USERID));
				Columns[2] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.FIRSTNAME));
				Columns[3] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.LASTNAME));
				Columns[4] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.EMAILID));
				Columns[5] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.PASSWORD));
			} while (cursor.moveToNext());
		} else {
			for (int i = 0; i < Columns.length; i++)
				Columns[i] = "None Found";
		}

		cursor.close();
		return Columns;
	}

	// Insert new user into table.
	// context = getapplicationcontext() // use as parameter in Android activity
	// Parameters are as Follows
	// tq=TotalQuestions
	// tc=TotalCorrect
	// ts=TotalScore
	// te=TotalEasy
	// tm=TotalMedium
	// th=TotalHard
	// es=EasyScore
	// ms=MediumScore
	// hs=HardScore
	public static void insertResult(Context context, String userID, String tq,
			String tc, String ts, String te, String tm, String th, String es,
			String ms, String hs) {
		ContentValues cv = new ContentValues();

		cv.put(EquestDbHelper.USERID, userID);
		cv.put(EquestDbHelper.TOTALQUESTIONS, tq);
		cv.put(EquestDbHelper.TOTALCORRECT, tc);
		cv.put(EquestDbHelper.TOTALSCORE, ts);
		cv.put(EquestDbHelper.TOTALEASY, te);
		cv.put(EquestDbHelper.TOTALMEDIUM, tm);
		cv.put(EquestDbHelper.TOTALHARD, th);
		cv.put(EquestDbHelper.EASYSCORE, es);
		cv.put(EquestDbHelper.MEDIUMSCORE, ms);
		cv.put(EquestDbHelper.HARDSCORE, hs);

		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.RESULT_TABLE);
		context.getContentResolver().insert(contentUri, cv);
	}

	// Gets Result by ID
	// context = getapplicationcontext() // use as parameter in Android activity
	// Returns an Array of Strings Such that,
	// String[0]=UserID
	// String[1]=TotalQuestions
	// String[2]=TotalCorrect
	// String[3]=TotalScore
	// String[4]=TotalEasy
	// String[5]=TotalMedium
	// String[6]=TotalHard
	// String[7]=EasyScore
	// String[8]=MediumScore
	// String[9]=HardScore
	public static String[] getResultByUserID(Context context, String userid) {

		String Columns[] = { 
				EquestDbHelper.USERID,
				EquestDbHelper.TOTALQUESTIONS, 
				EquestDbHelper.TOTALCORRECT,
				EquestDbHelper.TOTALSCORE, 
				EquestDbHelper.TOTALEASY,
				EquestDbHelper.TOTALMEDIUM, 
				EquestDbHelper.TOTALHARD,
				EquestDbHelper.EASYSCORE, 
				EquestDbHelper.MEDIUMSCORE,
				EquestDbHelper.HARDSCORE };

		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.RESULT_TABLE);
		String selection = EquestDbHelper.USERID + " = " + userid;

		Cursor cursor = context.getContentResolver().query(contentUri, Columns,
				selection, null, null);
		if (cursor.moveToFirst()) {
			do {
				Columns[0] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.USERID));
				Columns[1] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALQUESTIONS));
				Columns[2] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALCORRECT));
				Columns[3] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALSCORE));
				Columns[4] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALEASY));
				Columns[5] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALMEDIUM));
				Columns[6] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.TOTALHARD));
				Columns[7] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.EASYSCORE));
				Columns[8] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.MEDIUMSCORE));
				Columns[9] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.HARDSCORE));
			} while (cursor.moveToNext());
		} else {
			for (int i = 0; i < Columns.length; i++)
				Columns[i] = "None Found";
		}

		cursor.close();
		return Columns;
	}

	/********************************************** UPDATED FUNCTIONS **************************************/

	// Returns an array of Strings of all users
	// If there is no user in table it will return an array of one string which
	// contains "None Found"
	public static String[] getAllUsers(Context context) {

		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.USER_TABLE);
		Cursor cursor = context.getContentResolver().query(contentUri,
				new String[] { EquestDbHelper.USERID }, null, null, null);
		int x = cursor.getCount();
		if (x == 0)
			return new String[] { "None Found" };
		String Users[] = new String[x];
		int i = 0;
		if (cursor.moveToFirst()) {
			do {
				Users[i] = cursor.getString(cursor
						.getColumnIndex(EquestDbHelper.USERID));
				i++;
			} while (cursor.moveToNext());
		}

		return Users;
	}

	// The following Functions are self explanatory
	public static void deleteAllUsers(Context context) {
		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.USER_TABLE);
		context.getContentResolver().delete(contentUri, null, null);
	}

	public static void deleteAllResults(Context context) {
		Uri contentUri = Uri.withAppendedPath(EquestProvider.CONTENT_URI,
				EquestDbHelper.RESULT_TABLE);
		context.getContentResolver().delete(contentUri, null, null);
	}

	public static void deleteAllUsersAndTables(Context context) {
		deleteAllUsers(context);
		deleteAllResults(context);
	}

}
