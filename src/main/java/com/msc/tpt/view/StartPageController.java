package com.msc.tpt.view;

import java.util.logging.Logger;

import com.msc.tpt.AppController;

import javafx.fxml.FXML;

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

	@FXML
	private void newTestProcedurePlan()
	{
		final AppController appController = AppController.getInstance();
		appController.loadView(View.CREATE_NEW);
	}

	@Override
	public void close()
	{
		// TODO
	}
}
