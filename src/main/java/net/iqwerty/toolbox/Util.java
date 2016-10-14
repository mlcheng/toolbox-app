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

	static boolean serviceIsRunning(Context context, Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if(serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
