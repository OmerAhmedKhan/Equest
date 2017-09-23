package com.equest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class EquestProvider extends ContentProvider {
	SQLiteOpenHelper dbHelper;
	public static final String AUTHORITY = "EquestContentProviderAuthority"; 
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY );
	
	@Override
	public boolean onCreate() {
		dbHelper = new EquestDbHelper(getContext());
		return true;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String TableName = uri.getLastPathSegment();
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		long value = database.insert(TableName, null, values);
		dbHelper.close();
		return Uri.withAppendedPath(CONTENT_URI, String.valueOf(value));
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String TableName = uri.getLastPathSegment();
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.delete(TableName, selection, selectionArgs);
		dbHelper.close();
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
		      		String[] selectionArgs, String sortOrder) {
		String TableName = uri.getLastPathSegment();
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(TableName,  projection, selection, selectionArgs, null, null, sortOrder);
		
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

}
