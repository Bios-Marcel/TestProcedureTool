package com.msc.tpt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msc.tpt.constants.PathConstants;
import com.msc.tpt.logging.TPTLogger;
import com.msc.tpt.logging.TPTUncaughtExceptionHandler;
import com.msc.tpt.settings.Settings;
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
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Settings settingsInstance;

	private static ResourceBundle language;

	/**
	 * @param args
	 *            currently unused
	 */
	public static void main(final String[] args)
	{
		/*
		 * Fixes font rendering TODO(MSC) See if this is the case on other machines
		 * aswell.
		 * On my Ubuntu Laptop, this doesn't seem to hapen, yet it happened on a
		 * multitude of windows machines.
		 */
		// System.setProperty("prism.lcdtext", "false");

		launch(args);
	}

	@Override
	public void start(final Stage primaryStage)
	{
		/*
		 * At this point, no exceptions can be caught by the uncaught exceptionhandler,
		 * so all of
		 * the things are executed within the try-catch phrase
		 */

		AppController.getInstance().setStage(primaryStage);
		startApplication();
	}

	public static void startApplication()
	{
		try
		{
			new File(PathConstants.TPT_HOME).mkdir();
			loadSettings();
			TPTLogger.setup();
			Thread.setDefaultUncaughtExceptionHandler(new TPTUncaughtExceptionHandler());

			// Load language files
			final Locale locale = settingsInstance.getLanguage();
			language = ResourceBundle.getBundle(PathConstants.LANGUAGE, locale);

			Font.loadFont(StartPageController.class.getResource(PathConstants.FONT_AWESOME).toExternalForm(), 12);
			prepareStageAndScene(AppController.getInstance().getPrimaryStage());

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
	private static void prepareStageAndScene(final Stage primaryStage)
	{
		primaryStage.setScene(new Scene(new Pane()));
		primaryStage.getIcons().add(new Image(Main.class.getResource(PathConstants.APP_ICON).toExternalForm()));
		primaryStage.setTitle(getString("app.title"));
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
	}

	public static void loadSettings() throws IOException
	{
		final File configFile = new File(PathConstants.SETTINGS_FILE);
		try
		{
			final ObjectMapper mapper = new ObjectMapper();
			settingsInstance = mapper.readValue(configFile, Settings.class);
		}
		catch (@SuppressWarnings("unused") final FileNotFoundException exception)
		{
			settingsInstance = new Settings();
			saveSettings();
			loadSettings();
		}
		catch (final IOException exception)
		{
			logger.log(Level.SEVERE, "Error loading settings", exception);
		}
	}

	public static void saveSettings()
	{
		try
		{
			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(PathConstants.SETTINGS_FILE), settingsInstance);
		}
		catch (final IOException exception)
		{
			logger.log(Level.SEVERE, "Error loading settings", exception);
		}
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

	public static ResourceBundle getLanguage()
	{
		return language;
	}
}
