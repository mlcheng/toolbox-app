package net.iqwerty.toolbox;

import android.app.ActivityManager;
import android.content.Context;

import java.io.File;

/**
 * Created by Michael on 10/10/2016.
 */

class Util {
	static void createDirectory(final String directory) {
		File _t = new File(directory);
		if (!_t.exists()) {
			_t.mkdirs();
		}
	}

	static void scanMedia(final String path) {

	}

	static boolean serviceIsRunning(final Context context, final Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if(serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	static int restoreOverlayColorFor(final String color, final Context context) {
		int n = Preferences.getInstance().getInt(context, color);

		if(n == Integer.MIN_VALUE) {
			if(color.equals(Config.SETTING_OVERLAY_COLOR_ALPHA)) {
				return Config.DEFAULT_FILTER_ALPLHA;
			} else if(color.equals(Config.SETTING_OVERLAY_COLOR_RED)) {
				return Config.DEFAULT_FILTER_RED;
			} else if(color.equals(Config.SETTING_OVERLAY_COLOR_GREEN)) {
				return Config.DEFAULT_FILTER_GREEN;
			} else if(color.equals(Config.SETTING_OVERLAY_COLOR_BLUE)) {
				return Config.DEFAULT_FILTER_BLUE;
			}
		}
		return n;
	}
}
