package com.example.menetrend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.menetrend.buszDBS.BuszTable;
import com.example.menetrend.kivetelesnapDBS.KivetelesNapTable;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "notetable.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		BuszTable.onCreate(database);
		KivetelesNapTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		BuszTable.onUpgrade(database, oldVersion, newVersion);
	}
}
