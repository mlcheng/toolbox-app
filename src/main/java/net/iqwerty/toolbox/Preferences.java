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

	void saveString(final Context context, String key, final String value) {
		getPreferenceEditor(context).putString(key, value).apply();
	}

	void saveBoolean(final Context context, final String key, final boolean value) {
		getPreferenceEditor(context).putBoolean(key, value).apply();
	}

	void saveInt(final Context context, final String key, final int value) {
		getPreferenceEditor(context).putInt(key, value).apply();
	}

	String getString(final Context context, final String key) {
		return getPreferenceManager(context).getString(key, null);
	}

	boolean getBoolean(final Context context, final String key) {
		return getPreferenceManager(context).getBoolean(key, false);
	}

	int getInt(final Context context, final String key) {
		return getPreferenceManager(context).getInt(key, Integer.MIN_VALUE);
	}

	private SharedPreferences getPreferenceManager(final Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private SharedPreferences.Editor getPreferenceEditor(final Context context) {
		return getPreferenceManager(context).edit();
	}
}
