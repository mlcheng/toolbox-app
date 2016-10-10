package net.iqwerty.toolbox;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Michael on 10/10/2016.
 */

public class EyeProtectionService extends Service {
	private EyeProtectionOverlay _overlay;

	@Override
	public void onCreate() {
//		super.onCreate();
//		_overlay = new EyeProtectionOverlay(this);
//
//		NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext())
//				.setSmallIcon(R.drawable.material_drawer_badge)
//				.setContentTitle(getString(R.string.app_name))
//				.setContentText(getString(R.string.app_name))
//				.setPriority(Notification.PRIORITY_MIN);
//
//		startForeground(1, notification.build());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		_overlay.hideOverlay(false);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.log("Starting service");
		if(intent != null) {
			Bundle extras = intent.getExtras();
			if(extras != null) {
				if(extras.getBoolean(Config.SERVICE_TOGGLE)) {
					_overlay.toggleOverlay(false);
				} else if(extras.getBoolean(Config.SERVICE_ON)) {
					_overlay.showOverlay(true);
				} else if(extras.getBoolean(Config.SERVICE_OFF)) {
					_overlay.hideOverlay(true);
				}
			} else {
				_overlay = new EyeProtectionOverlay(this);

				Sleeper sleeper = new Sleeper(this);
				sleeper.start();

				if(sleeper.shouldShowOverlay()) {
					_overlay.showOverlay(true);
				} else {
					_overlay.hideOverlay(true);
				}

				startForeground(1, sleeper.getNotification().build());
			}
		}
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
