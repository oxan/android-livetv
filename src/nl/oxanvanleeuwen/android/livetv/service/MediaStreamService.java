package nl.oxanvanleeuwen.android.livetv.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

public class MediaStreamService {
	private String baseURL;
	private ArrayList<Channel> channellist;
	
	public MediaStreamService(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public List<Channel> getChannels() throws IOException, JSONException {
		if(channellist != null)
			return channellist;
		channellist = new ArrayList<Channel>();
		String json = Util.getHttpResource(baseURL + "/json/GetChannels");
		JSONTokener tokener = new JSONTokener(json);
		JSONArray results = (JSONArray) tokener.nextValue();
		for(int i = 0; i < results.length(); i++) {
			channellist.add(new Channel(results.getJSONObject(i)));
		}
		return channellist;
	}
	
	public List<Channel> getChannelsCached() {
		return channellist;
	}
	
	private static class Util {
		private static HttpClient http;
	
		private static String getHttpResource(String url) throws IOException {
			if(http == null)
				http = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			
			InputStream data = http.execute(request).getEntity().getContent();
			byte[] buffer = new byte[1000];
			StringBuilder output = new StringBuilder();
			int numRead = 0;
			while((numRead = data.read(buffer, 0, 1000)) >= 0)
				output.append(new String(buffer, 0, numRead));
			return output.toString();
		}
	}
}
