package com.msc.tpt.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.msc.tpt.constants.PathConstants;

/**
 * Sets up the logger for this application.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public class TPTLogger
{
	static private FileHandler		fileTxt;
	static private SimpleFormatter	formatterTxt;

	/**
	 * Initializes the logger.
	 * <p>
	 * <ul>
	 * <li>Sets the {@link Level Log-Level}</li>
	 * <li>Sets the Formatter</li>
	 * <li>Sets up a filehandler</li>
	 * </ul>
	 * </p>
	 *
	 * @throws IOException
	 *             if the file handler couldn't be hooked up
	 */
	public static void setup() throws IOException
	{
		final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler(PathConstants.LOG_FILE, true);

		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
	}
}