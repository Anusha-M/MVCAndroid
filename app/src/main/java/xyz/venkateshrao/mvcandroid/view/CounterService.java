package xyz.venkateshrao.mvcandroid.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.shipdream.lib.android.mvc.view.MvcService;

import javax.inject.Inject;

import xyz.venkateshrao.mvcandroid.R;
import xyz.venkateshrao.mvcandroid.controller.CounterController;

/**
 * Created by venkatesh on 17/9/15.
 */
public class CounterService extends MvcService {

    @Inject
    private CounterController counterController;

    private final static int NOTIFICATION_ID = 0;
    private Handler handler;
    private static final int AUTO_FINISH_COUNT = 10;
    private AutoCounter autoCounter;
    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startAutoIncrement();
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAutoIncrement();
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotification(int currentCount) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Count to 10")
                .setContentText("Current count: " + currentCount)
                .setSmallIcon(R.drawable.ic_notification_auto_count)
                .setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void onEvent(CounterController.EventC2V.OnCounterUpdated event) {
        updateNotification(event.getCount());
    }

    private void startAutoIncrement() {
        stopAutoIncrement();
        updateNotification(counterController.getModel().getCount());
        autoCounter = new AutoCounter();
        autoCounter.run();
    }

    private void stopAutoIncrement() {
        if (autoCounter != null) {
            autoCounter.cancel();
        }
    }

    private class AutoCounter implements Runnable {
        private int count = 0;
        private boolean cancelled = false;

        @Override
        public void run() {
            if (!cancelled) {
                if (count++ <= AUTO_FINISH_COUNT) {
                    counterController.increment(this);
                    handler.postDelayed(this, 1000);
                } else {
                    stopSelf();
                }
            }
        }

        private void cancel() {
            cancelled = true;
        }
    }

}
