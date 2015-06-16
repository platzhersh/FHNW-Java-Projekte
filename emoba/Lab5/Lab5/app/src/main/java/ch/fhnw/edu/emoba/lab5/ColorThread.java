package ch.fhnw.edu.emoba.lab5;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class ColorThread extends HandlerThread {
    private static final String TAG = "ColorThread";
    public static final String COLOR_VALUE = "ColorValue";
    public static final int STOP_COLOR_THREAD = 0;
    public static final int NEW_COLOR = 1;

    private Handler mHandler;
    private Handler mainThreadHandler;

    private boolean error = false;
    private boolean isCancelled = false;

    public ColorThread(String name) {
        super(name);
        this.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == STOP_COLOR_THREAD) {
                    isCancelled = true;
                }
            }
        };
    }

    public void setMainThreadHandler(Handler mainThreadHandler) {
        this.mainThreadHandler = mainThreadHandler;
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void run() {
        RGBColor actColor = new RGBColor();

        while (!error && !isCancelled) {
            try {
                actColor.r = (int) Math.round(Math.random() * 255);
                actColor.g = (int) Math.round(Math.random() * 255);
                actColor.b = (int) Math.round(Math.random() * 255);
                if (mainThreadHandler != null) {
                    Message msg = mainThreadHandler.obtainMessage();
                    msg.what = NEW_COLOR;
                    Bundle data = new Bundle();
                    data.putSerializable(COLOR_VALUE, actColor);
                    msg.setData(data);
                    msg.sendToTarget();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error = false;
            }
        }


    }
}
