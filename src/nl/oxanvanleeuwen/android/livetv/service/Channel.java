package nl.oxanvanleeuwen.android.livetv.service;

import org.json.JSONException;
import org.json.JSONObject;

public class Channel {
	public int id;
	public String displayName;
	public String name;
	public boolean isTv;
	public boolean isRadio;
	
	public Channel(JSONObject data) throws JSONException {
		id = data.getInt("IdChannel");
		displayName = data.getString("DisplayName");
		name = data.getString("Name");
		isTv = data.getBoolean("IsTv");
		isRadio = data.getBoolean("IsRadio");
	}
}
