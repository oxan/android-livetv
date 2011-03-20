package nl.oxanvanleeuwen.android.livetv.service;

import org.json.JSONException;
import org.json.JSONObject;

public class Transcoder {
	public int id;
	public String name;
	public boolean usesTranscoding;
	
	public Transcoder(JSONObject data) throws JSONException {
		id = data.getInt("Id");
		name = data.getString("Name");
		usesTranscoding = data.getBoolean("UsesTranscoding");
	}
}
