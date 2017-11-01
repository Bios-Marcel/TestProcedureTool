package com.msc.tpt;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.msc.tpt.constants.PathConstants;
import com.msc.tpt.logging.TPTLogger;
import com.msc.tpt.logging.TPTUncaughtExceptionHandler;
import com.msc.tpt.view.StartPageController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Initializes and launches the application.
 *
 * @author Marcel
 * @since 26.10.2017
 */
public class Main extends Application
{
	private static final Logger	logger	= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static ResourceBundle		language;

	@Override
	public void start(final Stage primaryStage)
	{
		/*
		 * At this point, no exceptions can be caught by the uncaught exceptionhandler,
		 * so all of
		 * the things are executed within the try-catch phrase
		 */

		try
		{
			new File(PathConstants.TPT_HOME).mkdir();
			TPTLogger.setup();
			Thread.setDefaultUncaughtExceptionHandler(new TPTUncaughtExceptionHandler());

			// Load language files
			final Locale locale = new Locale("en"); // TODO Make this configurable
			language = ResourceBundle.getBundle(PathConstants.LANGUAGE, locale);

			Font.loadFont(StartPageController.class.getResource(PathConstants.FONT_AWESOME).toExternalForm(), 12);
			prepareStageAndScene(primaryStage);

			AppController.getInstance().setStage(primaryStage);
			AppController.getInstance().start();
		}
		catch (final Exception exception)
		{
			logger.log(Level.SEVERE, "Error while starting up application", exception);
		}
	}

	/**
	 * Performs a onetime setup on the given {@link Stage}.
	 *
	 * @param primaryStage
	 *            stage to be configured
	 */
	private void prepareStageAndScene(final Stage primaryStage)
	{
		primaryStage.setScene(new Scene(new Pane()));
		primaryStage.getIcons().add(new Image(Main.class.getResource(PathConstants.APP_ICON).toExternalForm()));
		primaryStage.setTitle(getString("app.title"));
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
	}

	/**
	 * @param args
	 *            currently unused
	 */
	public static void main(final String[] args)
	{
		/*
		 * Fixes font rendering TODO(MSC) See if this is the case on other machines
		 * aswell. On my Ubuntu Laptop, this doesn't seem to hapen, yet it happened on a
		 * multitude of windows machines.
		 */
		// System.setProperty("prism.lcdtext", "false");

		launch(args);
	}

	/**
	 * Returns a localized string.
	 *
	 * @param key
	 *            the key for the string
	 * @return the String that represents the key for the specific language
	 */
	public static String getString(final String key)
	{
		return language.getString(key);
	}
}
