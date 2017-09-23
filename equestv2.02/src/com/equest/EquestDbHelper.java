package com.equest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EquestDbHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "equest.db";
	public static final int DB_VERSION = 1;
	
	// The following are the Constants & Columns of 'User' table.
	public static final String USER_TABLE = "USERS";
	public static final String ID_USER_TABLE = "id";
	public static final String USERID = "user_id";
	public static final String FIRSTNAME = "user_fname";
	public static final String LASTNAME = "user_lname";
	public static final String EMAILID = "email_id";
	public static final String PASSWORD = "password";
	
	// The following are the Constants & Columns of 'Result' table.
	public static final String RESULT_TABLE = "RESULT";
	public static final String TOTALQUESTIONS = "total_questions";
	public static final String TOTALCORRECT = "total_correct";
	public static final String TOTALSCORE = "total_score";
	public static final String TOTALEASY = "total_easy";
	public static final String TOTALMEDIUM = "total_medium";
	public static final String TOTALHARD = "total_hard";
	public static final String EASYSCORE = "easy_score";
	public static final String MEDIUMSCORE = "medium_score";
	public static final String HARDSCORE = "hard_score";
	
	
	public EquestDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		
		// Query to create 'User' Table
		String CREATE_USER_TABLE = 
				"CREATE TABLE " + USER_TABLE + " ( " + 
						ID_USER_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						USERID + " TEXT NOT NULL, " +
						FIRSTNAME + " TEXT NOT NULL, " +
						LASTNAME + " TEXT NOT NULL, " +
						EMAILID + " TEXT NOT NULL, " +
						PASSWORD + " TEXT NOT NULL" +
						" ); ";
		
		// Query to create 'Result' Table
		String CREATE_RESULT_TABLE = 
				"CREATE TABLE " + RESULT_TABLE + " ( " + 
						USERID + " TEXT NOT NULL, " +
						TOTALQUESTIONS + " TEXT NOT NULL, " +
						TOTALCORRECT + " TEXT NOT NULL, " +
						TOTALSCORE + " TEXT NOT NULL, " +
						TOTALEASY + " TEXT NOT NULL, " +
						TOTALMEDIUM + " TEXT NOT NULL, " +
						TOTALHARD + " TEXT NOT NULL, " +
						EASYSCORE + " TEXT NOT NULL, " +
						MEDIUMSCORE + " TEXT NOT NULL, " +
						HARDSCORE + " TEXT NOT NULL " +
						" ); ";
		
		
		// Creates the 'Users' Table
		db.execSQL(CREATE_USER_TABLE);
		
		// Creates the 'Result' Table
		db.execSQL(CREATE_RESULT_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + RESULT_TABLE);
		onCreate(db);
	}

}
