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

	private Service _context;
	private View _overlay;
	private boolean _isShown;
	private WindowManager _windowManager;

	private final int FILTER = Color.argb(36, 255, 40, 30);
	private final int TRANSPARENT = Color.argb(0, 0, 0, 0);

	EyeProtectionOverlay(final Service context) {
		_context = context;
		_windowManager = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
	}

	void showOverlay(final int transitionDuration) {

		if (!_isShown) {
			_overlay = new View(_context);
			ObjectAnimator transition = ObjectAnimator.ofObject(_overlay, "backgroundColor", new ArgbEvaluator(), TRANSPARENT, FILTER);
			transition.setDuration(transitionDuration);
			transition.start();

			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.MATCH_PARENT, // Can't be wrap content otherwise some parts won't get filtered (left and right)
					getScreenHeight(_windowManager) + (getNavigationBarHeight() * 4),
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

			_windowManager.addView(_overlay, params);
			_isShown = true;
		}
	}

	void hideOverlay(final int transitionDuration) {
		if (_overlay != null) {
			try {
				if (_isShown) {
					ObjectAnimator transition = ObjectAnimator.ofObject(_overlay, "backgroundColor", new ArgbEvaluator(), FILTER, TRANSPARENT);
					transition.setDuration(transitionDuration);
					transition.start();
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							_windowManager.removeView(_overlay);
						}
					}, transitionDuration);
					_isShown = false;
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	void toggleOverlay(final int transitionDuration) {
		if (_isShown) {
			hideOverlay(transitionDuration);
		} else {
			showOverlay(transitionDuration);
		}
	}

	private int getNavigationBarHeight() {
		Resources resources = _context.getResources();
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
}
