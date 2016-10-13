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
	public void onDestroy() {
		Logger.log("Destroying service");

		_overlay.hideOverlay(Config.OVERLAY_TRANSITION_NONE);
		_overlay = null;

		super.onDestroy();
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Logger.log("Starting service");
		super.onStartCommand(intent, flags, startId);

		_overlay = new EyeProtectionOverlay(this);

		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				if (extras.getBoolean(Config.SERVICE_TOGGLE)) {
					// From notification toggle
					_overlay.toggleOverlay(Config.OVERLAY_TRANSITION_SHORT);
				} else if (extras.getBoolean(Config.SERVICE_ON)) {
					// From alarm
					_overlay.showOverlay(Config.OVERLAY_TRANSITION_LONG);
				} else if (extras.getBoolean(Config.SERVICE_OFF)) {
					// From alarm
					_overlay.hideOverlay(Config.OVERLAY_TRANSITION_LONG);
				}
			} else {
				Sleeper sleeper = new Sleeper(this);
				sleeper.start();

				// From service start
				if (sleeper.shouldShowOverlay()) {
					_overlay.showOverlay(Config.OVERLAY_TRANSITION_SHORT);
				} else {
					_overlay.hideOverlay(Config.OVERLAY_TRANSITION_SHORT);
				}

				startForeground(1, sleeper.getNotification().build());
			}
		}
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}
}
