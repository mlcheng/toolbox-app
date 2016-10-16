package net.iqwerty.toolbox;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.AbstractBadgeableDrawerItem;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static android.R.attr.fragment;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Michael on 10/11/2016.
 */

class NavigationDrawer {
	private static Drawer _navigationDrawer;
	private static IDrawerItem _mainPosition;

	public static Drawer getNavigationDrawer() {
		return _navigationDrawer;
	}

	public static void setNavigationDrawer(final Drawer drawer) {
		_navigationDrawer = drawer;
	}

	static void setupNavigationDrawer(final AppCompatActivity context) {

		/* Drawer items */
		PrimaryDrawerItem home = toolboxDrawerItem(
				new PrimaryDrawerItem(),
				1,
				R.string.app_name
		);
		PrimaryDrawerItem settings = toolboxDrawerItem(
				new PrimaryDrawerItem(),
				2,
				R.string.settings
		);

		SecondaryDrawerItem screenOff = toolboxDrawerItem(
				new SecondaryDrawerItem(),
				3,
				R.string.screen_off
		);
		SecondaryDrawerItem screenshot = toolboxDrawerItem(
				new SecondaryDrawerItem(),
				4,
				R.string.screenshot
		);


		final Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
		context.setSupportActionBar(toolbar);

		_navigationDrawer = new DrawerBuilder()
				.withActivity(context)
				.withToolbar(toolbar)
				.addDrawerItems(home, settings, new DividerDrawerItem(), screenOff, screenshot)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(final View view, final int position, final IDrawerItem drawerItem) {
						Logger.log(Integer.toString(position));
						Intent intent;

						if (drawerItem == _mainPosition) {
							_navigationDrawer.closeDrawer();
							return true;
						}
						
						switch (position) {
							case 0:
								// Home
								switchFragment(context, MainFragment.newInstance());
								_mainPosition = drawerItem;
								toolbar.setTitle(R.string.app_name);
								break;
							case 1:
								// Settings
								switchFragment(context, SettingsFragment.newInstance());
								_mainPosition = drawerItem;
								toolbar.setTitle(R.string.settings);
								break;
							case 3:
								// Screen off
								intent = new Intent(context, ScreenOffActivity.class);
								context.startActivity(intent);
								_navigationDrawer.setSelection(_mainPosition);
								break;
							case 4:
								// Screenshot
								intent = new Intent(context, ScreenshotActivity.class);
								context.startActivity(intent);
								_navigationDrawer.setSelection(_mainPosition);
								break;
							default:
								break;
						}
						_navigationDrawer.closeDrawer();
						return true;
					}
				})
				.build();
		_navigationDrawer.setSelection(1);
	}

	/**
	 * Build the Toolbox drawer items according to app style
	 *
	 * @param drawerItem An untouched drawer item
	 * @param id         The identifier for the drawer item
	 * @param name       The label for the drawer item
	 * @return Returns the drawer item
	 */
	private static <T extends AbstractBadgeableDrawerItem> T toolboxDrawerItem(final T drawerItem, final long id, final int name) {
		drawerItem.withIdentifier(id);
		drawerItem.withName(name);
//		drawerItem.withSelectedColor(Color.argb(255, 100, 0, 0));
		return drawerItem;
	}

	private static void switchFragment(final AppCompatActivity context, Fragment fragment) {
		FragmentManager manager = context.getSupportFragmentManager();
		manager
				.beginTransaction()
				.setCustomAnimations(R.anim.enter, R.anim.exit)
				.replace(R.id.layout_injector, fragment)
				.commit();
	}

}
