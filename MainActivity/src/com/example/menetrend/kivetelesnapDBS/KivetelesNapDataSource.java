package com.example.menetrend.kivetelesnapDBS;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.menetrend.DBHelper;

public class KivetelesNapDataSource {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { KivetelesNapTable.COLUMN_ID,
			KivetelesNapTable.COLUMN_DATUM, KivetelesNapTable.COLUMN_KOZLEKEDIK };

	public KivetelesNapDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public String[] getColumnsName() {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ KivetelesNapTable.TABLE_NOTE + " LIMIT 1", null);
		return cursor.getColumnNames();
	}

	public KivetelesNap createNote(KivetelesNap nap) {
		ContentValues values = new ContentValues();
		values.put(KivetelesNapTable.COLUMN_DATUM, nap.getDatum());
		values.put(KivetelesNapTable.COLUMN_KOZLEKEDIK, nap.getKözlekedik());
		long insertId = database.insert(KivetelesNapTable.TABLE_NOTE, null,
				values);
		Cursor cursor = database.query(KivetelesNapTable.TABLE_NOTE,
				allColumns, KivetelesNapTable.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		KivetelesNap newNote = cursorToNap(cursor);
		cursor.close();
		return newNote;
	}

	private KivetelesNap cursorToNap(Cursor cursor) {
		KivetelesNap nap = new KivetelesNap();
		nap.setId(cursor.getInt(0));
		nap.setDatum(cursor.getString(1));
		nap.setKözlekedik(cursor.getString(2));
		return nap;
	}

	public void deleteNote(KivetelesNap nap) {
		long id = nap.getId();
		database.delete(KivetelesNapTable.TABLE_NOTE,
				KivetelesNapTable.COLUMN_ID + " = " + id, null);
	}

	public List<KivetelesNap> getAllKivetelesNap() {
		List<KivetelesNap> naplist = new ArrayList<KivetelesNap>();

		Cursor cursor = database.query(KivetelesNapTable.TABLE_NOTE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			KivetelesNap note = cursorToNap(cursor);
			naplist.add(note);
			cursor.moveToNext();
		}

		cursor.close();
		return naplist;
	}

	public String getSpecialTimetable(String date) {
		String ret = "";
		Cursor cursor = database.query(KivetelesNapTable.TABLE_NOTE,
				allColumns, KivetelesNapTable.COLUMN_DATUM + "=" + date, null,
				null, null, null);
		try {
			cursor.moveToFirst();
			KivetelesNap note = cursorToNap(cursor);
			ret=note.közlekedik;
		} catch (Exception ex) {
			Log.v("KivételesNapDataSourca exception","üzenete: "+ex.getMessage());
			ret = null;
		}

		cursor.close();
		return ret;
	}
}
