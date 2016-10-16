package net.iqwerty.toolbox;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Michael on 10/10/2016.
 */

class EyeProtectionOverlay {

	private static EyeProtectionOverlay instance = null;

	private final int TRANSPARENT = Color.argb(0, 0, 0, 0);

	private EyeProtectionOverlay() {
	}

	// The singleton class
	static EyeProtectionOverlay getInstance() {
		if (instance == null) {
			return new EyeProtectionOverlay();
		}
		return instance;
	}

	View showOverlay(final Service context, final int transitionDuration) {
		View overlay;
		final WindowManager windowManager = getWindowManager(context);

		Logger.log(Integer.toString(getFilterColor(context)));

		overlay = new View(context);
		ObjectAnimator transition = ObjectAnimator.ofObject(overlay, "backgroundColor", new ArgbEvaluator(), TRANSPARENT, getFilterColor(context));
		transition.setDuration(transitionDuration);
		transition.start();

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, // Can't be wrap content otherwise some parts won't get filtered (left and right)
				getScreenHeight(windowManager) + (getNavigationBarHeight(context) * 4),
				0,
				0,
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
						| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				//WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT
		);

		windowManager.addView(overlay, params);
		return overlay;
	}

	void hideOverlay(final Service context, final View overlay, final int transitionDuration) {
		final WindowManager windowManager = getWindowManager(context);
		if (overlay != null) {
			try {
				ObjectAnimator transition = ObjectAnimator.ofObject(overlay, "backgroundColor", new ArgbEvaluator(), getFilterColor(context), TRANSPARENT);
				transition.setDuration(transitionDuration);
				transition.start();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						windowManager.removeView(overlay);
					}
				}, transitionDuration);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	View toggleOverlay(final Service context, final View overlay, final int transitionDuration) {
		Logger.log("Overlay is shown: " + (overlay != null));
		if (overlay != null) {
			hideOverlay(context, overlay, transitionDuration);
			return null;
		} else {
			return showOverlay(context, transitionDuration);
		}
	}

	private WindowManager getWindowManager(final Service context) {
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	private int getNavigationBarHeight(final Service context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			return resources.getDimensionPixelSize(resourceId);
		}
		return 0;
	}

	private int getScreenHeight(final WindowManager wm) {
		Display display = wm.getDefaultDisplay();

		Point size = new Point();
		display.getSize(size);

		return size.y;
	}

	private int getFilterColor(final Service context) {
		return Color.argb(
				Util.restoreOverlayColorFor(Config.SETTING_OVERLAY_COLOR_ALPHA, context),
				Util.restoreOverlayColorFor(Config.SETTING_OVERLAY_COLOR_RED, context),
				Util.restoreOverlayColorFor(Config.SETTING_OVERLAY_COLOR_GREEN, context),
				Util.restoreOverlayColorFor(Config.SETTING_OVERLAY_COLOR_BLUE, context)
		);
	}
}
