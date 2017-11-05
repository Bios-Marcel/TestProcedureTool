package com.msc.tpt.fxutil;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.msc.tpt.Main;
import com.msc.tpt.view.ViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public interface FXUtil
{
	static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Pane loadFXML(final ViewController controller, final URL fxmlLocation)
	{
		return loadFXML(controller, fxmlLocation, null);
	}

	public static Pane loadFXML(final ViewController controller, final URL fxmlLocation, final String cssLocation)
	{
		final FXMLLoader loader = new FXMLLoader();
		// Always using the currently set language.
		loader.setResources(Main.getLanguage());

		try
		{
			loader.setController(controller);
			loader.setLocation(fxmlLocation);

			final Pane toLoad = loader.load();
			if (Objects.nonNull(cssLocation))
			{
				toLoad.getStylesheets().setAll(cssLocation);
			}
			return toLoad;
		}
		catch (final IOException exception)
		{
			logger.log(Level.SEVERE, "Error while loading view: '" + fxmlLocation + "'", exception);
			throw new RuntimeException(exception);
		}
	}
}
