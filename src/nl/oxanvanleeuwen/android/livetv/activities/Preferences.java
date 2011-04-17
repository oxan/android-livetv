package nl.oxanvanleeuwen.android.livetv.activities;

import java.util.ArrayList;
import nl.oxanvanleeuwen.android.livetv.R;
import nl.oxanvanleeuwen.android.livetv.service.MediaStreamService;
import nl.oxanvanleeuwen.android.livetv.service.Transcoder;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
	private static final String TAG = "Preferences";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		generateTranscoderList(preferences);
		preferences.registerOnSharedPreferenceChangeListener(new TranscoderListUpdater());
	}
	
	private void generateTranscoderList(SharedPreferences preferences) {
		if(preferences == null)
			preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(!preferences.getString("address", "").equals("")) {
			Log.v(TAG, "Generating transcoder list...");
			MediaStreamService service = new MediaStreamService(preferences.getString("address", "") + "MediaStream.svc");
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> ids = new ArrayList<String>();
			try {
				for(Transcoder transcoder : service.getTranscoderList()) {
					names.add(transcoder.name);
					ids.add(Integer.toString(transcoder.id));
				}
				ListPreference transcoders = (ListPreference)findPreference("transcoder");
				transcoders.setEntries((String[])names.toArray(new String[names.size()]));
				transcoders.setEntryValues((String[])ids.toArray(new String[ids.size()]));
				Log.v(TAG, "Generated transcoder list");
			} catch(Exception e) {
				Log.e(TAG, "Couldn't get transcoder list from server", e);
				Toast.makeText((Activity)this, "Server doesn't response or server URL is invalid", Toast.LENGTH_LONG);
			}
		}
	}
	
	private void generateTranscoderList() { 
		generateTranscoderList(null);
	}
	
	private class TranscoderListUpdater implements SharedPreferences.OnSharedPreferenceChangeListener {
		private static final String TAG = "TranscoderListUpdater";
		
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			Log.v(TAG, "Changed: " + key);
			if(key.equals("address"))
				generateTranscoderList();
		}
	}
}
