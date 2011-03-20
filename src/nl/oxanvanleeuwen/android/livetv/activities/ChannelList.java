package nl.oxanvanleeuwen.android.livetv.activities;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import nl.oxanvanleeuwen.android.livetv.R;
import nl.oxanvanleeuwen.android.livetv.service.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class ChannelList extends Activity {
	private static final String TAG = "ChannelListActivity";
	
	private MediaStreamService service;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channellist);
        ListView lv = (ListView)findViewById(R.id.channellist);
        
        // set up service
        service = new MediaStreamService("http://mediastreamer.lan/MPWebStream/MediaStream.svc");
        
        // show all channels
        try {
            Log.v(TAG, "Started loading channels");
            ArrayList<String> channels = new ArrayList<String>();
			for(Channel channel : service.getChannels()) {
				channels.add(channel.name);
			}
			String[] strlist = new String[]{};
			ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, channels.toArray(strlist));
			lv.setAdapter(items);
			Log.v(TAG, "Loaded channels");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// register callback
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Channel channel = service.getChannelsCached().get(position);
				Intent intent = new Intent(ChannelList.this, ViewStream.class);
				intent.putExtra("url", "bla");
				startActivity(intent);
			}
		});
    }
}