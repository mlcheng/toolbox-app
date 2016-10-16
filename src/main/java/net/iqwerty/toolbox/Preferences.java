package net.iqwerty.toolbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Michael on 10/10/2016.
 */

class Preferences {
	private static Preferences instance = null;

	private Preferences() {

	}

	static Preferences getInstance() {
		if(instance == null) {
			return new Preferences();
		}
		return instance;
	}

	static void save(Context context, String key, Object value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		if(value instanceof String) {
			editor.putString(key, (String) value);
		} else if(value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}
		editor.apply();
	}

	static <T extends Object> T get(Context context, String key, T type) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if(type == String.class) {
			preferences.getString(key, null);
		}

		return null;
	}
}
