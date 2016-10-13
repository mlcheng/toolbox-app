package net.iqwerty.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Michael on 10/11/2016.
 */

public class ScreenshotActivity extends Activity {
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Turn off filter first
		stopService(new Intent(this, EyeProtectionService.class));

		// Take the screenshot
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				takeScreenshot();

				// Restart the filter
				startService(new Intent(getApplicationContext(), EyeProtectionService.class));
			}
		}, Config.SCREENSHOT_DELAY);

		finish();
	}

	private void takeScreenshot() {
		final String filename = generateFilename();
		final String path = System.getenv("EXTERNAL_STORAGE") + "/Pictures/Screenshots/";
		Util.createDirectory(path);

		try {
			// Take screenshot
			Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + path + filename).getBytes("ASCII"));
			os.flush();
			os.close();
			sh.waitFor();

			// Create notification

			// Scan media
			Util.scanMedia("file://" + path + filename);
		} catch (final IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String generateFilename() {
		Calendar time = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
		return "Screenshot_" + formatter.format(time.getTime()) + ".png";
	}
}
