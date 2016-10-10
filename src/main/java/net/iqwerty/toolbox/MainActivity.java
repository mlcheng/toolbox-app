package net.iqwerty.toolbox;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	PermissionManager _pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Util.setupNavigationDrawer(this);

		if(PermissionManager.canDrawOverlays(this)) {
			startService(new Intent(this, EyeProtectionService.class));
		} else {
			_pm = new PermissionManager(this);
			_pm.requestOverlay();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == Config.REQUEST_OVERLAY) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if(!Settings.canDrawOverlays(this)) {
					Toast.makeText(this, getString(R.string.need_overlay_permission), Toast.LENGTH_LONG).show();
				} else {
					startService(new Intent(this, EyeProtectionService.class));
				}
			}
		}
	}
}
