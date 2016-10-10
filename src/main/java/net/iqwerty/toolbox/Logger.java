package net.iqwerty.toolbox;

import android.util.Log;

/**
 * Created by Michael on 10/10/2016.
 */

public class Logger {
	public static int log(String message) {
		return Log.v(Config.APP, message);
	}
}
