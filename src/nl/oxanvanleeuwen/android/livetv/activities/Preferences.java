package nl.oxanvanleeuwen.android.livetv.activities;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import nl.oxanvanleeuwen.android.livetv.R;
import nl.oxanvanleeuwen.android.livetv.service.MediaStreamService;
import nl.oxanvanleeuwen.android.livetv.service.Transcoder;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(!preferences.getString("address", "").equals("")) {
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
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
