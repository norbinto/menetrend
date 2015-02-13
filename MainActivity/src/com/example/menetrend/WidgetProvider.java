package com.example.menetrend;

import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.menetrend.R;

public class WidgetProvider extends AppWidgetProvider{
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v("WIDGET","Widget lefut");
		// ID-k lekérdezése
				ComponentName thisWidget = new ComponentName(context,
						WidgetProvider.class);
				int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
				for (int widgetId : allWidgetIds) {
					RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
							R.layout.widget_layout);
					// Szöveg beállítása
					remoteViews.setTextViewText(R.id.tvWidOda, new Date(System.currentTimeMillis()).toLocaleString());
					
					// Kattintás esemény kezelése, hatására frissül ismét a widget
					Intent intent = new Intent(context, WidgetProvider.class);
			
					intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
					intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			
					PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
							0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					remoteViews.setOnClickPendingIntent(R.id.widgetID, pendingIntent);
					appWidgetManager.updateAppWidget(widgetId, remoteViews);
				}

	}

}
