package com.example.menetrend.buszDBS;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BuszTable {
	public static final String TABLE_NOTE = "buszTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_INDUL = "indul";
	public static final String COLUMN_NAP = "nap";
	public static final String COLUMN_HONNAN = "honnan";
	public static final String COLUMN_HOVA = "hova";
	public static final String COLUMN_KERESZTUL = "keresztul";
	public static final String COLUMN_MENETREND_ID = "menetrend_id";

	private static final String BUSZTABLE_CREATE = "create table " + TABLE_NOTE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
				  + COLUMN_INDUL + " text , " 
			      + COLUMN_NAP	+ " integer , " 
				  + COLUMN_HONNAN + " text , "
				  + COLUMN_HOVA + " text , " 
				  + COLUMN_KERESZTUL + " text , "
				  + COLUMN_MENETREND_ID + " integer "+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(BUSZTABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BuszTable.class.getName(), "Upgradingfrom version " + oldVersion
				+ " to " + newVersion);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
		onCreate(database);
	}
}