package com.example.menetrend.buszDBS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.menetrend.DBHelper;

public class BuszDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { BuszTable.COLUMN_ID,
			BuszTable.COLUMN_INDUL, BuszTable.COLUMN_NAP,
			BuszTable.COLUMN_HONNAN, BuszTable.COLUMN_HOVA,
			BuszTable.COLUMN_KERESZTUL, BuszTable.COLUMN_MENETREND_ID };

	public BuszDataSource(Context context) {
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
				+ BuszTable.TABLE_NOTE + " LIMIT 1", null);
		return cursor.getColumnNames();
	}

	public Busz createNote(Busz busz) {
		ContentValues values = new ContentValues();
		values.put(BuszTable.COLUMN_INDUL, busz.getIndul());
		values.put(BuszTable.COLUMN_NAP, busz.getNap());
		values.put(BuszTable.COLUMN_HONNAN, busz.getHonnan());
		values.put(BuszTable.COLUMN_HOVA, busz.getHova());
		values.put(BuszTable.COLUMN_KERESZTUL, busz.getKeresztul());
		values.put(BuszTable.COLUMN_MENETREND_ID, busz.getMenetrend_id());
		long insertId = database.insert(BuszTable.TABLE_NOTE, null, values);
		Cursor cursor = database.query(BuszTable.TABLE_NOTE, allColumns,
				BuszTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Busz newNote = cursorToBusz(cursor);
		cursor.close();
		return newNote;
	}

	private Busz cursorToBusz(Cursor cursor) {
		Busz busz = new Busz();
		busz.setId(cursor.getInt(0));
		busz.setIndul(cursor.getString(1));
		busz.setNap(cursor.getInt(2));
		busz.setHonnan(cursor.getString(3));
		busz.setHova(cursor.getString(4));
		busz.setKeresztul(cursor.getString(5));
		busz.setMenetrend_id(cursor.getInt(6));
		return busz;
	}

	public void deleteNote(Busz busz) {
		long id = busz.getId();
		database.delete(BuszTable.TABLE_NOTE, BuszTable.COLUMN_ID + " = " + id,
				null);
	}

	public List<Busz> getAllBusz() {
		List<Busz> buszlist = new ArrayList<Busz>();

		Cursor cursor = database.query(BuszTable.TABLE_NOTE, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Busz note = cursorToBusz(cursor);
			buszlist.add(note);
			cursor.moveToNext();
		}
		// Ne felejts�k bez�rni!
		cursor.close();
		return buszlist;
	}

	public LinkedList<Busz> getDailyBusz(String data) {
		LinkedList<Busz> buszlist = new LinkedList<Busz>();
		String selection = "";
		for (int i = 0; i < data.length(); i++) {
			if (i != 0) {
				selection += " or ";
			}
			selection += BuszTable.COLUMN_NAP + "=" + data.charAt(i);
		}

		Cursor cursor = database.query(BuszTable.TABLE_NOTE, allColumns,
				selection, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Busz note = cursorToBusz(cursor);
			buszlist.add(note);
			cursor.moveToNext();
		}

		cursor.close();
		return buszlist;
	}
}
