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
		if (instance == null) {
			return new Preferences();
		}
		return instance;
	}

	void saveString(Context context, String key, String value) {
		getPreferenceEditor(context).putString(key, value).apply();
	}

	void saveBoolean(Context context, String key, boolean value) {
		getPreferenceEditor(context).putBoolean(key, value).apply();
	}

	void saveInt(Context context, String key, int value) {
		getPreferenceEditor(context).putInt(key, value).apply();
	}

	String getString(Context context, String key) {
		return getPreferenceManager(context).getString(key, null);
	}

	boolean getBoolean(Context context, String key) {
		return getPreferenceManager(context).getBoolean(key, false);
	}

	int getInt(Context context, String key) {
		return getPreferenceManager(context).getInt(key, Integer.MIN_VALUE);
	}

	private SharedPreferences getPreferenceManager(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private SharedPreferences.Editor getPreferenceEditor(Context context) {
		return getPreferenceManager(context).edit();
	}

//	<T extends Object> T get(Context context, String key, T type) {
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		if (type == String.class) {
//			return (T) preferences.getString(key, null);
//		}
//
//		return null;
//	}
}
