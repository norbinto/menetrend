package com.example.menetrend.kivetelesnapDBS;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.menetrend.buszDBS.BuszTable;

public class KivetelesNapTable {
	public static final String TABLE_NOTE = "kivetelesNapTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATUM = "datum";
	public static final String COLUMN_KOZLEKEDIK = "kozlekedik";
	
	private static final String KIVETELESNAPTABLE_CREATE = "create table " + TABLE_NOTE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
				  + COLUMN_DATUM + " text , " 
			      + COLUMN_KOZLEKEDIK	+ " text ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(KIVETELESNAPTABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BuszTable.class.getName(), "Upgradingfrom version " + oldVersion
				+ " to " + newVersion);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
		onCreate(database);
	}
}
