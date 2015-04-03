package com.boakye.daniel.spotbot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocDatabaseHelper extends SQLiteOpenHelper {

	public static final String DEBUG_TAG = "location table";
	private static final String DATABASE_NAME = "loctable.db";
	private static final int DATABASE_VERSION = 2;

	public LocDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the com.boakye.daniel.spotbot.database
	@Override
	public void onCreate(SQLiteDatabase database) {
		LocTable.onCreate(database);
	}

	// Method is called during an upgrade of the com.boakye.daniel.spotbot.database,
	// e.g. if you increase the com.boakye.daniel.spotbot.database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		LocTable.onUpgrade(database, oldVersion, newVersion);
	}
}