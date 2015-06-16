package ch.fhnw.edu.emoba.lab6;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String TAG = "HelloWorld-MainActivity";
	private TextView helloWorldView;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			RGBColor color = (RGBColor) intent.getSerializableExtra(ColorService.COLOR_VALUE);
			if (color != null) {
				updateHelloWorldView(color);
			}
		}
	};

	private void updateHelloWorldView(RGBColor c) {
		helloWorldView.setBackgroundColor(Color.rgb(c.r,c.g, c.b));
		Log.d(TAG, c.r + " " + c.g + " " + c.b);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		helloWorldView = (TextView) findViewById(R.id.txtView);
		helloWorldView.setText("Hello World");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ColorService.NOTIFICATION));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
	}
	
	
	public void start(View view) {
		startService(new Intent(this, ColorService.class));
	}
	
	public void stop(View view) {
		stopService(new Intent(this, ColorService.class));
	}		
}
