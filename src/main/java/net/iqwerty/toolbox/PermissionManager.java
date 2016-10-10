package net.iqwerty.toolbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Michael on 10/10/2016.
 */

public class PermissionManager {
//	// TODO someday...
//	public static boolean request(Context context, String permission, boolean required) {
//		return true;
//	}

	private final Activity _activity;

	public PermissionManager(Activity activity) {
		_activity = activity;
	}

	public static boolean canDrawOverlays(Context context) {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context);
	}

	public void requestOverlay() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if(!Settings.canDrawOverlays(_activity)) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + _activity.getPackageName()));
				_activity.startActivityForResult(intent, Config.REQUEST_OVERLAY);
			}
		}
	}
}
