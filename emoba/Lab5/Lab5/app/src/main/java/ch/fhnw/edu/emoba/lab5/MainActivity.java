package ch.fhnw.edu.emoba.lab5;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	private TextView helloWorldView;
	private Handler workerThreadHandler = null;
    private Handler mainThreadHandler = null;

	private void updateHelloWorldView(RGBColor c) {
		helloWorldView.setBackgroundColor(Color.rgb(c.r,c.g, c.b));
		Log.d(TAG, c.toString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		helloWorldView = (TextView) findViewById(R.id.txtView);
		helloWorldView.setText("Hello World");

        mainThreadHandler = new Handler(Looper.getMainLooper()) {
//        mainThreadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "message received " + msg.what);
                switch (msg.what) {
                    case ColorThread.NEW_COLOR:
                        Bundle data = msg.getData();
                        if (data.containsKey(ColorThread.COLOR_VALUE)) {
                            final RGBColor color = (RGBColor) data.get(ColorThread.COLOR_VALUE);
                            updateHelloWorldView(color);
                        }
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };
	}

    public void start(View view) {
        Log.d(TAG, "start()");
        if (workerThreadHandler == null) {
            ColorThread colorThread = new ColorThread("ColorThread");
            colorThread.start();
            colorThread.setMainThreadHandler(mainThreadHandler);
            workerThreadHandler = colorThread.getHandler();
        }
    }

    public void stop(View view) {
        Log.d(TAG, "stop()");
        if (workerThreadHandler != null) {
            Message msg = workerThreadHandler.obtainMessage();
            msg.what = ColorThread.STOP_COLOR_THREAD;
            msg.sendToTarget();
            workerThreadHandler = null;
        }
	}

}
