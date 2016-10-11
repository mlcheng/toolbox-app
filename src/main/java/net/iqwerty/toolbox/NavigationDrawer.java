package net.iqwerty.toolbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Michael on 10/11/2016.
 */

public class NavigationDrawer {
	private static Drawer _navigationDrawer;

	public static Drawer getNavigationDrawer() {
		return _navigationDrawer;
	}

	public static void setNavigationDrawer(final Drawer drawer) {
		_navigationDrawer = drawer;
	}

	public static void setupNavigationDrawer(final AppCompatActivity context) {

		/* Drawer items */
		PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.app_name);
		PrimaryDrawerItem settings = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.settings);

		SecondaryDrawerItem screenOff = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.screen_off);
		SecondaryDrawerItem screenshot = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.screenshot);




		Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
		context.setSupportActionBar(toolbar);

		_navigationDrawer = new DrawerBuilder()
				.withActivity(context)
				.withToolbar(toolbar)
				.addDrawerItems(home, settings, new DividerDrawerItem(), screenOff, screenshot)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						Logger.log(Integer.toString(position));
						Intent intent;
						switch(position) {
							case 0:
								// Home
								break;
							case 1:
								// Settings
								break;
							case 3:
								// Screen off
								intent = new Intent(context, ScreenOffActivity.class);
								context.startActivity(intent);
								break;
							case 4:
								// Screenshot
								intent = new Intent(context, ScreenshotActivity.class);
								context.startActivity(intent);
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

}
