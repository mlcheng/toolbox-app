package net.iqwerty.toolbox;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Michael on 10/11/2016.
 */

public class ScreenOffActivity extends Activity {
	DevicePolicyManager devicePolicyManager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
			if (devicePolicyManager.isAdminActive(new ComponentName(this, AdminReceiver.class))) {
				devicePolicyManager.lockNow();
			} else {
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, AdminReceiver.class));
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.admin_permission_needed));
				startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finish();
	}
}
