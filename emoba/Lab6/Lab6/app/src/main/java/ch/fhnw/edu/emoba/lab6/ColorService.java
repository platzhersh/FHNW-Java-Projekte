package ch.fhnw.edu.emoba.lab6;

import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ColorService extends IntentService {
	private static String TAG = "ColorService";
	public static final String COLOR_VALUE = "COLOR";
	public static final String NOTIFICATION = "NEW_COLOR";

    private boolean running;

    public ColorService() {
        super("ColorService");
    }

    public ColorService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RGBColor color = new RGBColor();
        running = true;
        while (running) {
            try {
                color.r = (int) Math.round(Math.random() * 255);
                color.g = (int) Math.round(Math.random() * 255);
                color.b = (int) Math.round(Math.random() * 255);

                Intent colorMessage = new Intent(NOTIFICATION);
                colorMessage.putExtra(COLOR_VALUE, color);
                LocalBroadcastManager.getInstance(this).sendBroadcast(colorMessage);
                Log.d(TAG, "color is " + color);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
        running = false;
		Log.d(TAG, "onDestroy() called");
	}

}
