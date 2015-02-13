package updateDBS;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class DBSUpdater extends AsyncTask<String, Character, String> {

	public interface DownloadCompelteListener {
		public void onTaskComplate(String aResult);

		public void onTaskError(String aError);
	}

	private Context context;
	private DownloadCompelteListener listener;
	String error = null;
	String progress = null;

	public DBSUpdater(Context context, DownloadCompelteListener listener) {
		super();
		this.context = context;
		this.listener = listener;
	}

	public static boolean hasWIFIConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}
		return false;

	}

	@Override
	protected String doInBackground(String... params) {
		String ret = "";
		HttpClient hc = new DefaultHttpClient();
		InputStream is = null;
		HttpGet httpget = new HttpGet(params[0]);
		HttpResponse resp;

		try {
			resp = hc.execute(httpget);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity ent = resp.getEntity();
				if (ent != null) {
					is = ent.getContent();
					int ch;
					StringBuilder sb = new StringBuilder();
					while ((ch = is.read()) != -1) {
						sb.append((char) ch);
						// Log.v("DBSUPDATER ","ch: "+ch);
					}
					ret = sb.toString();
				} else
					error = "Entity is empty!";
			} else
				error = "Státuscode is not ok";
		} catch (Exception e) {
			error = e.getMessage();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
				}
			}
		}
//		Log.v("DBSUPDATER ", "ret: " + ret);
		return ret;
	}

	@Override
	protected void onProgressUpdate(Character... values) {
		progress += values[0];
		// progressDialoge.setMessage(progress);
	}

	@Override
	protected void onPostExecute(String result) {
		// progressDialoge.dismiss();
		if (error != null) {
			listener.onTaskError(error);
		} else {
			listener.onTaskComplate(result);
		}
	}
}
