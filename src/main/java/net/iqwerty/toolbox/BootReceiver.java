package net.iqwerty.toolbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Michael on 10/11/2016.
 */

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent intent) {
		Intent startEyeProtectionService = new Intent(context, EyeProtectionService.class);
		context.startService(startEyeProtectionService);
	}
}
