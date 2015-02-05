package com.example.menetrend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import updateDBS.DBSUpdater;
import updateDBS.DBSUpdater.DownloadCompelteListener;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.menetrend.buszDBS.Busz;
import com.example.menetrend.buszDBS.BuszDataSource;
import com.example.menetrend.fragmentek.ListázóFragment;
import com.example.menetrend.fragmentek.VálasztóFragment;
import com.example.menetrend.kivetelesnapDBS.KivetelesNap;
import com.example.menetrend.kivetelesnapDBS.KivetelesNapDataSource;

public class MainActivity extends FragmentActivity implements Communicator,
		DownloadCompelteListener {

	private static final String KEY_FIRST_RUN_FLAG = "key_first_run_flag";
	private static final String LAST_UPDATE = "last_update";
	private static final String DB_INIT = "DB_INIT";

	public static String TAG = "MainActivity";

	private DBSUpdater dbsUpdater = null;
	private BuszDataSource buszDataSource;
	private KivetelesNapDataSource kivnapDataSource;
	private PopupWindow popupWindow;
	private DatePicker dpDate;

	private int year;
	private int month;
	private int day;

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {

		dpDate = (DatePicker) findViewById(R.id.dpDate);

		return super.onCreateView(name, context, attrs);
	}

	public void setCurrentDateOnView() {

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		dpDate.init(year, month, day, null);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buszDataSource = new BuszDataSource(this);
		buszDataSource.open();
		kivnapDataSource = new KivetelesNapDataSource(this);
		kivnapDataSource.open();
		SharedPreferences sp = getSharedPreferences(DB_INIT, MODE_PRIVATE);
		boolean isFirstRun = sp.getBoolean(KEY_FIRST_RUN_FLAG, true);

		if (isFirstRun) {
			Toast.makeText(this, "Első indítás, adatbázis létrehozása!",
					Toast.LENGTH_LONG).show();
			{
				salata();
				Editor edtr = sp.edit();
				edtr.putBoolean(KEY_FIRST_RUN_FLAG, false);
				edtr.commit();
			}

		}
		if (DBSUpdater.hasWIFIConnection(this)) {
			dbsUpdater = new DBSUpdater(this, this);
			dbsUpdater.execute("http://norbinto.ddns.net:8080/napok.txt");
		}
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		VálasztóFragment vf = new VálasztóFragment();
		ft.add(R.id.frameLayout, vf, VálasztóFragment.TAG);
		ft.commit();

		// feliratkozás a wifi állapot változásra
		this.registerReceiver(this.mConnReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

			NetworkInfo currentNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

			if (currentNetworkInfo.isConnected()) {

				if (DBSUpdater.hasWIFIConnection(context)) {
					dbsUpdater = new DBSUpdater(context,
							(DownloadCompelteListener) context);
					dbsUpdater
							.execute("http://norbinto.ddns.net:8080/napok.txt");
				}
				Toast.makeText(getApplicationContext(), "Connected",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Esetleges adatbázis frissítéshez csatlakozzon wifi hálózathoz!",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@SuppressLint("SimpleDateFormat")
	private String koz() {
		String ret = "";
		kivnapDataSource.close();
		kivnapDataSource.open();

		Log.v("Simpledateformat",
				new SimpleDateFormat("yyyy-MM-dd").format(new Date(dpDate.getYear() - 1900,
						dpDate.getMonth(), dpDate.getDayOfMonth())));

		String extra = null;
		List<KivetelesNap> kk = kivnapDataSource.getAllKivetelesNap();
		for (int i = 0; i < kk.size(); i++) {
			if (kk.get(i).datum.equals((new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date(dpDate.getYear() - 1900,
							dpDate.getMonth(), dpDate.getDayOfMonth())))
					.toString())) {
				extra = kk.get(i).közlekedik;
			}
		}

		if (extra != null) {
			// Log.v("MAINACTIVITY KOZ", "EXTRA NAP JÖN");
			ret = extra;
		} else {
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				c.setTime(sdf.parse((dpDate.getYear())+"-"+(dpDate.getMonth()+1)+"-"+dpDate.getDayOfMonth()));
				Log.e("C settime után",c.getTime().toString());
			} catch (ParseException e) {
				Log.e("CALENDAR PARSE ERROR",e.toString());
				e.printStackTrace();
			}// all done
			
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			Log.v("DAYOFWEEK",dayOfWeek+"");
			switch (dayOfWeek) {
			case Calendar.SUNDAY:
				ret = "0127";
				break;
			case Calendar.SATURDAY:
				ret = "0567";
				break;

			default:
				ret = "02346";
				break;
			}
		}
		return ret;
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			unregisterReceiver(mConnReceiver);
		} catch (RuntimeException ex) {
			Log.e("MAINACTIVITY onStop",
					"Broadcastreciever leiratkozás probléma: " + ex.toString());
		}
		SharedPreferences sp = getSharedPreferences(DB_INIT, MODE_PRIVATE);
		Editor edtr = sp.edit();
		edtr.putBoolean(KEY_FIRST_RUN_FLAG, false);
		edtr.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);// Menu Resource, Menu
		return true;
	}

	public void onClick(View v) {
		try {
		} catch (Exception ex) {
			Log.e("MAINACTIVITY popupbezárás", ex.toString());
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.info_popup, null);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void üzenet(String data) {

		ListázóFragment lf = new ListázóFragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		ft.replace(R.id.frameLayout, lf, "asd");
		ft.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		ft.addToBackStack("Választófragment->Listázófragment");
		ft.commit();
		LinkedList<Busz> sámsonDebrecen = buszDataSource.getDailyBusz(koz());

		if (lf != null)
			lf.állító(data, sámsonDebrecen);

	}

	@Override
	public void onTaskComplate(String aResult) {
		String[] tmp = aResult.split(";");
		SharedPreferences sp = getSharedPreferences(DB_INIT, MODE_PRIVATE);
		int lastUpdate = sp.getInt(LAST_UPDATE, 0);
		if (lastUpdate < Integer.parseInt(tmp[0])) {
			Toast.makeText(this, "Adatbázis frissítve", Toast.LENGTH_LONG)
					.show();
			{
				for (int i = 1; i < tmp.length; i++) {
					kivnapDataSource.createNote(new KivetelesNap(tmp[i],
							tmp[i + 1]));
					i++;
				}
				Editor edtr = sp.edit();
				edtr.putInt(LAST_UPDATE, Integer.parseInt(tmp[0]));
				edtr.commit();
			}
		}
		List<KivetelesNap> k = kivnapDataSource.getAllKivetelesNap();
		Log.v("MAINACTIVITY",
				"kivételesnap size k: " + k.get(k.size() - 2).datum
						+ " közlekedik: " + k.get(k.size() - 2).közlekedik);
		koz();

	}

	@Override
	public void onTaskError(String aError) {
		Toast.makeText(this, aError, Toast.LENGTH_LONG).show();
	}

	public void salata() {

		// datasource2.createNote(new KivetelesNap("2015-01-10", "02346"));

		buszDataSource.createNote(new Busz("04:15", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("04:30", 4, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("04:15", 0, "SZ", "DB", "", 2));
		buszDataSource.createNote(new Busz("04:50", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("05:00", 5, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("05:10", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("05:15", 1, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("05:30", 2, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("05:45", 2, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:00", 6, "HS", "DB", "", 0));

		buszDataSource.createNote(new Busz("06:15", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:30", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:05", 7, "SZ", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:40", 3, "SK", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:40", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:45", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("06:25", 7, "NA", "DB", "", 2));
		buszDataSource.createNote(new Busz("07:00", 4, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("07:00", 3, "NA", "DB", "", 0));
		buszDataSource.createNote(new Busz("07:30", 6, "HS", "DB", "", 0));

		buszDataSource.createNote(new Busz("07:45", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("07:45", 2, "NA", "DB", "", 0));// PROBL�M�S!!!!!!
		buszDataSource.createNote(new Busz("08:00", 0, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("08:30", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("08:45", 1, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("09:00", 6, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("09:00", 6, "NA", "DB", "", 0));// Sz�nd�kosan
																			// NA
																			// mert
																			// itt
																			// �ll
																			// 5
																			// percet
		buszDataSource.createNote(new Busz("09:30", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("09:30", 7, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("10:00", 0, "NA", "DB", "", 2));// probl�m�s++++++
		buszDataSource.createNote(new Busz("10:00", 4, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("10:30", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("10:30", 5, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("11:00", 7, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("11:15", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("11:30", 5, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("12:00", 4, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("12:00", 7, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("12:05", 4, "NA", "DB", "", 2));// Sz�nd�kosan
																			// NA
																			// mert
																			// itt
																			// �ll
																			// 5
																			// percet
		buszDataSource.createNote(new Busz("12:30", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("12:25", 7, "NA", "DB", "", 2));

		buszDataSource.createNote(new Busz("12:45", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("13:00", 5, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("13:00", 3, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("13:15", 2, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("13:30", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("13:20", 4, "SZ", "DB", "", 3));
		buszDataSource.createNote(new Busz("13:45", 4, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("14:00", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("14:00", 7, "HS", "DB", "", 0));// Sz�nd�kosan
																			// NA
																			// mert
																			// itt
																			// �ll
																			// 5
																			// percet
		buszDataSource.createNote(new Busz("14:15", 4, "HS", "DB", "", 0));

		buszDataSource.createNote(new Busz("13:55", 4, "SZ", "DB", "", 3)); // PROBL�M�S!!!
		buszDataSource.createNote(new Busz("14:45", 0, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("15:00", 3, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("15:15", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("15:05", 3, "SZ", "DB", "", 3));
		buszDataSource.createNote(new Busz("15:30", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("15:25", 1, "NA", "DB", "", 2));
		buszDataSource.createNote(new Busz("15:45", 3, "HS", "DB", "", 0));

		buszDataSource.createNote(new Busz("16:00", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("15:45", 6, "SZ", "DB", "", 0));
		buszDataSource.createNote(new Busz("16:15", 1, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("16:30", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("16:45", 3, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("16:25", 4, "SZ", "DB", "", 0));
		buszDataSource.createNote(new Busz("17:00", 7, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("17:15", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("17:30", 6, "HS", "DB", "", 0));

		buszDataSource.createNote(new Busz("17:25", 2, "NA", "DB", "", 0));
		buszDataSource.createNote(new Busz("18:00", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("18:25", 4, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("18:30", 5, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("18:30", 7, "HK", "DB", "", 0));

		buszDataSource.createNote(new Busz("19:00", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("19:15", 1, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("19:30", 6, "HS", "DB", "", 0));
		buszDataSource.createNote(new Busz("20:00", 0, "HK", "DB", "", 0));
		buszDataSource.createNote(new Busz("20:20", 0, "NA", "DB", "", 0));

		buszDataSource.createNote(new Busz("21:35", 2, "HS", "DB", "", 0));

		/*------------------------------------------------------------*/
		// Debrecen-Hajdúsámson
		buszDataSource.createNote(new Busz("04:05", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("04:30", 1, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("04:45", 6, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("05:00", 2, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("05:15", 6, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("05:30", 0, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("05:45", 0, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("06:05", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("06:15", 6, "DB", "HS", "", 0));

		buszDataSource.createNote(new Busz("06:45", 4, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("06:45", 0, "DB", "NA", "", 0));
		buszDataSource.createNote(new Busz("07:00", 3, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("07:15", 0, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("07:30", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("07:45", 5, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("07:45", 4, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("08:00", 1, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("08:15", 6, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("08:45", 3, "DB", "HS", "", 0));

		buszDataSource.createNote(new Busz("08:45", 7, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("08:55", 0, "DB", "NA", "", 0));
		buszDataSource.createNote(new Busz("09:15", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("09:30", 1, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("09:45", 3, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("09:45", 5, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("10:00", 4, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("10:15", 7, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("10:20", 4, "DB", "HS", "", 0));

		buszDataSource.createNote(new Busz("10:45", 5, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("10:45", 4, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("11:15", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("11:45", 0, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("12:15", 3, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("12:15", 5, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("12:20", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("12:30", 2, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("12:45", 6, "DB", "HS", "", 0));

		buszDataSource.createNote(new Busz("13:00", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("13:15", 3, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("13:15", 7, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("13:30", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("13:45", 6, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("14:00", 2, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("14:15", 3, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("14:15", 5, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("14:25", 1, "DB", "NA", "", 0));

		buszDataSource.createNote(new Busz("14:30", 4, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("14:45", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("14:45", 5, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("15:00", 3, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("15:15", 6, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("15:30", 2, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("15:45", 6, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("16:00", 3, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("16:15", 6, "DB", "HK", "", 0));

		buszDataSource.createNote(new Busz("16:20", 2, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("16:30", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("16:45", 6, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("17:00", 1, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("17:00", 3, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("17:05", 6, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("17:15", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("17:30", 3, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("17:45", 0, "DB", "HK", "", 0));

		buszDataSource.createNote(new Busz("18:00", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("18:15", 6, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("18:30", 2, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("18:45", 5, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("18:45", 2, "DB", "SZ", "", 0));
		buszDataSource.createNote(new Busz("19:15", 4, "DB", "HS", "", 0));
		buszDataSource.createNote(new Busz("19:20", 5, "DB", "NA", "", 0));
		buszDataSource.createNote(new Busz("19:30", 4, "DB", "NA", "", 0));
		buszDataSource.createNote(new Busz("19:50", 7, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("19:55", 4, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("20:50", 0, "DB", "HS", "", 0));

		buszDataSource.createNote(new Busz("22:20", 4, "DB", "HK", "", 0));
		buszDataSource.createNote(new Busz("22:40", 0, "DB", "SZ", "", 0));

	}

}
