package nl.oxanvanleeuwen.android.livetv.activities;

import nl.oxanvanleeuwen.android.livetv.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewStream extends Activity {
    private static final String TAG = "ViewStream";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        
        Log.v(TAG, "Got view");
        VideoView view = (VideoView)findViewById(R.id.video);
        
        Log.v(TAG, "Creating MediaController");
        MediaController mc = new MediaController(this);
        
        Log.v(TAG, "Setting MediaController");
        view.setMediaController(mc);
        
        Log.v(TAG, "Setting URI");
        view.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
        
        Log.v(TAG, "Requesting focus");
        view.requestFocus();
        
        Log.v(TAG, "Starting");
        view.start();
    }
}
