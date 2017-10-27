package com.msc.tpt.constants;

import java.io.File;

/**
 * Contains internal and external file paths.
 *
 * @author Marcel
 * @since 27.10.2017
 */
@SuppressWarnings("javadoc")
public class PathConstants
{
	public static final String	TPT_HOME	= System.getProperty("user.home") + File.separator + ".tpt";
	public static final String	LOG_FILE	= TPT_HOME + File.separator + "Log.txt";

	public static final String	APP_ICON		= "/com/msc/tpt/icon/appicon.png";
	public static final String	FONT_AWESOME	= "/com/msc/tpt/fonts/FontAwesome.otf";
	public static final String	LANGUAGE		= "com.msc.tpt.language.Lang";

}
