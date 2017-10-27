package com.msc.tpt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.msc.tpt.view.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Handles basic view unspecific GUI control.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public final class AppController
{
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static Stage primaryStage;

	/**
	 * Sets the primary state for furhter interaction.
	 *
	 * @param stage
	 *            {@link Stage} that will be set
	 */
	public static final void setStage(final Stage stage)
	{
		primaryStage = stage;
	}

	/**
	 * Initially launches the GUI.
	 */
	public static final void start()
	{
		loadView(View.START_PAGE);
		primaryStage.show();
	}

	/**
	 * Loads a view in the current {@link #primaryStage Stage}.
	 *
	 * @param view
	 *            the view to be loaded
	 */
	public static void loadView(final View view)
	{
		logger.info("Loading view: '" + view + "'");

		final FXMLLoader loader = new FXMLLoader();
		loader.setResources(Main.language);

		try
		{
			loader.setController(view.getControllerType().newInstance());
			loader.setLocation(AppController.class.getResource(view.getFXMLPath()));

			final Parent toLoad = loader.load();
			toLoad.getStylesheets().setAll(view.getStylesheetPath());
			primaryStage.getScene().setRoot(toLoad);
		}
		catch (final IOException | InstantiationException | IllegalAccessException exception)
		{
			logger.log(Level.SEVERE, "Error while loading view: '" + view + "'", exception);
		}
	}
}
