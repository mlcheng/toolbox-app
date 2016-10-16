package net.iqwerty.toolbox;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Created by Michael on 10/10/2016.
 */

class Sleeper {
	private Service _context;
	private NotificationCompat.Builder _notification;
	private boolean _shouldShowOverlay;

	Sleeper(final Service context) {
		_context = context;
		_shouldShowOverlay = false;
	}

	void start() {
		PendingIntent morning = newServicePendingIntent(2, Config.SERVICE_OFF);
		PendingIntent night = newServicePendingIntent(1, Config.SERVICE_ON);
		PendingIntent toggle = newServicePendingIntent(0, Config.SERVICE_TOGGLE);
		PendingIntent screenshot = PendingIntent.getActivity(
				_context,
				3,
				new Intent(_context, ScreenshotActivity.class),
				0
		);

		setSchedules(Config.MORNING, morning, Config.NIGHT, night);

		buildNotification(toggle, screenshot);
	}

	boolean shouldShowOverlay() {
		return _shouldShowOverlay;
	}

	private void setSchedules(final int morningTime, final PendingIntent morningIntent, final int nightTime, final PendingIntent nightIntent) {
		Calendar morning = Calendar.getInstance();
		Calendar night = Calendar.getInstance();
		Calendar now = Calendar.getInstance();

		int currentHour = now.get(Calendar.HOUR_OF_DAY);
		if (currentHour < morningTime || currentHour >= nightTime) {
			_shouldShowOverlay = true;
		}

		if (currentHour >= morningTime) {
			if (currentHour >= nightTime) {
				// The next night alarm is the next day
				night.add(Calendar.DATE, 1);
			}
			// The next day alarm is the next day
			morning.add(Calendar.DATE, 1);
		}

		night = initializeAlarm(night, nightTime);
		morning = initializeAlarm(morning, morningTime);

		AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(morningIntent);
		alarmManager.cancel(nightIntent);
		setRepeatingAlarm(alarmManager, morning, morningIntent);
		setRepeatingAlarm(alarmManager, night, nightIntent);
	}

	private void setRepeatingAlarm(final AlarmManager alarmManager, final Calendar time, final PendingIntent intent) {
		alarmManager.setInexactRepeating(
				AlarmManager.RTC_WAKEUP,
				time.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY,
				intent
		);
	}

	private Calendar initializeAlarm(final Calendar alarm, final int hour) {
		alarm.set(Calendar.HOUR_OF_DAY, hour);
		alarm.set(Calendar.MINUTE, 0);
		alarm.set(Calendar.SECOND, 0);
		return alarm;
	}

	private void buildNotification(final PendingIntent toggle, final PendingIntent screenshot) {
		_notification = new NotificationCompat.Builder(_context.getApplicationContext())
				.setSmallIcon(R.drawable.ic_stat_overlay)
				.setContentTitle(_context.getString(R.string.app_name))
				.setContentText(_context.getString(R.string.toggle_filter))
				.setContentIntent(toggle)
				.setStyle(new NotificationCompat.BigTextStyle()
						.bigText(_context.getString(R.string.toggle_filter))
				)
				.addAction(R.drawable.ic_stat_screenshot, _context.getString(R.string.take_screenshot), screenshot)
				.setPriority(Notification.PRIORITY_MIN);
	}

	NotificationCompat.Builder getNotification() {
		return _notification;
	}

	private PendingIntent newServicePendingIntent(final int requestCode, final String extra) {
		return PendingIntent.getService(_context, requestCode, new Intent(
				_context,
				EyeProtectionService.class
		).putExtra(extra, true), PendingIntent.FLAG_CANCEL_CURRENT);
	}
}
