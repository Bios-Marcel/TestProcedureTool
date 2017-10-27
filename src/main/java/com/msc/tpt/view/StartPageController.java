package com.msc.tpt.view;

import java.util.logging.Logger;

/**
 * Handles all user interaction on the applications start page.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public class StartPageController implements ViewController
{
	private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void initialize()
	{
		logger.info("Initializing start page.");
	}
}
