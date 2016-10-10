package net.iqwerty.toolbox;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Michael on 10/10/2016.
 */

public class Util {
	private static Drawer _navigationDrawer;

	public static Drawer getNavigationDrawer() {
		return _navigationDrawer;
	}

	public static void setNavigationDrawer(Drawer drawer) {
		_navigationDrawer = drawer;
	}

	public static void setupNavigationDrawer(AppCompatActivity context) {
		PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.app_name);
		PrimaryDrawerItem settings = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.settings);

		Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
		context.setSupportActionBar(toolbar);

		_navigationDrawer = new DrawerBuilder()
				.withActivity(context)
				.withToolbar(toolbar)
				.addDrawerItems(home, settings)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						_navigationDrawer.closeDrawer();
						return true;
					}
				})
				.build();
		_navigationDrawer.setSelection(1);
	}
}
