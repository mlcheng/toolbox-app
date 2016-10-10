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

public class Sleeper {
	private Service _context;
	private NotificationCompat.Builder _notification;
	private boolean _shouldShowOverlay;

	public Sleeper(Service context) {
		_context = context;
		_shouldShowOverlay = false;
	}

	public void start() {
		PendingIntent morning = newPendingIntent(2, Config.SERVICE_OFF);
		PendingIntent night = newPendingIntent(1, Config.SERVICE_ON);
		PendingIntent toggleService = newPendingIntent(0, Config.SERVICE_TOGGLE);

		setSchedules(Config.MORNING, morning, Config.NIGHT, night);

		buildNotification(morning, night, toggleService);
	}

	public boolean shouldShowOverlay() {
		return _shouldShowOverlay;
	}

	private void setSchedules(int morningTime, PendingIntent morningIntent, int nightTime, PendingIntent nightIntent) {
		Calendar morning = Calendar.getInstance();
		Calendar night = Calendar.getInstance();
		Calendar now = Calendar.getInstance();

		int currentHour = now.get(Calendar.HOUR_OF_DAY);
		if(currentHour < morningTime || currentHour >= nightTime) {
			_shouldShowOverlay = true;
		}

		if(currentHour >= morningTime) {
			if(currentHour >= nightTime) {
				// The next night alarm is the next day
				night.add(Calendar.DATE, 1);
			}
			// The next day alarm is the next day
			morning.add(Calendar.DATE, 1);
		}

		night = setAlarm(night, nightTime);
		morning = setAlarm(morning, morningTime);

		AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(morningIntent);
		alarmManager.cancel(nightIntent);
		alarmManager.setInexactRepeating(
				AlarmManager.RTC_WAKEUP,
				morning.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY,
				morningIntent
		);
		alarmManager.setInexactRepeating(
				AlarmManager.RTC_WAKEUP,
				night.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY,
				nightIntent
		);

	}

	private Calendar setAlarm(Calendar alarm, int hour) {
		alarm.set(Calendar.HOUR_OF_DAY, hour);
		alarm.set(Calendar.MINUTE, 0);
		alarm.set(Calendar.SECOND, 0);
		return alarm;
	}

	private void buildNotification(PendingIntent morning, PendingIntent night, PendingIntent toggle) {
		_notification = new NotificationCompat.Builder(_context.getApplicationContext())
				.setSmallIcon(R.drawable.material_drawer_badge)
				.setContentTitle(_context.getString(R.string.app_name))
				.setContentText(_context.getString(R.string.app_name))
				.setPriority(Notification.PRIORITY_MIN);
	}

	public NotificationCompat.Builder getNotification() {
		return _notification;
	}

	private PendingIntent newPendingIntent(int requestCode, String extra) {
		return PendingIntent.getService(_context, requestCode, new Intent(
				_context,
				EyeProtectionService.class
		).putExtra(extra, true), PendingIntent.FLAG_CANCEL_CURRENT);
	}
}
