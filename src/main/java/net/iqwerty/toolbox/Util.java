package net.iqwerty.toolbox;

import java.io.File;

/**
 * Created by Michael on 10/10/2016.
 */

public class Util {
	public static void createDirectory(final String directory) {
		File _t = new File(directory);
		if(!_t.exists()) {
			_t.mkdirs();
		}
	}

	public static void scanMedia(final String path) {

	}
}
