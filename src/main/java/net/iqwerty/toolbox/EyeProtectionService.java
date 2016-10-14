package net.iqwerty.toolbox;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Michael on 10/10/2016.
 */

public class EyeProtectionService extends Service {

	private View _overlay = null;

	@Override
	public void onDestroy() {
		Logger.log("Destroying service");

		EyeProtectionOverlay overlay = EyeProtectionOverlay.getInstance();
		overlay.hideOverlay(this, _overlay, Config.OVERLAY_TRANSITION_NONE);
		_overlay = null;

		super.onDestroy();
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Logger.log("Starting service");
		super.onStartCommand(intent, flags, startId);

		EyeProtectionOverlay overlay = EyeProtectionOverlay.getInstance();

		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				if (extras.getBoolean(Config.SERVICE_TOGGLE)) {
					// From notification toggle
					_overlay = overlay.toggleOverlay(this, _overlay, Config.OVERLAY_TRANSITION_SHORT);
				} else if (extras.getBoolean(Config.SERVICE_ON)) {
					// From alarm
					_overlay = overlay.showOverlay(this, Config.OVERLAY_TRANSITION_LONG);
				} else if (extras.getBoolean(Config.SERVICE_OFF)) {
					// From alarm
					overlay.hideOverlay(this, _overlay, Config.OVERLAY_TRANSITION_LONG);
					_overlay = null;
				}
			} else {
				Sleeper sleeper = new Sleeper(this);
				sleeper.start();

				// From service start
				if (sleeper.shouldShowOverlay()) {
					_overlay = overlay.showOverlay(this, Config.OVERLAY_TRANSITION_SHORT);
				} else {
					overlay.hideOverlay(this, _overlay, Config.OVERLAY_TRANSITION_SHORT);
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
