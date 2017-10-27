package com.msc.tpt.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds all the important information related to every single view.
 *
 * @author Marcel
 */
@SuppressWarnings("javadoc")
public enum View
{
	START_PAGE(0, StartPageController.class, "/com/msc/tpt/fxml/StartPage.fxml", "/com/msc/tpt/style/application.css");

	private final int						id;
	private Class<? extends ViewController>	controllerType;
	private String							stylesheetPath;
	private String							fxmlPath;

	private final static Map<Integer, View> ID_MAPPING = new ConcurrentHashMap<>();

	// Create Mapping in order to be able to find an enum value by simply providing
	// its id.
	static
	{
		for (final View view : View.values())
		{
			View.ID_MAPPING.put(view.getId(), view);
		}
	}

	private View(final int id, final Class<? extends ViewController> controllerType,
			final String fxmlPath, final String stylesheetPathCss)
	{
		this.id = id;
		this.controllerType = controllerType;
		this.stylesheetPath = stylesheetPathCss;
		this.fxmlPath = fxmlPath;
	}

	/**
	 * @return ID of the View
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @return the {@link ViewController} Type to be used for this View
	 */
	public Class<? extends ViewController> getControllerType()
	{
		return controllerType;
	}

	/**
	 * @return the path to the css stylesheet of this View
	 */
	public String getStylesheetPath()
	{
		return stylesheetPath;
	}

	/**
	 * @return the path to the FMXL file of this View
	 */
	public String getFXMLPath()
	{
		return fxmlPath;
	}

	/**
	 * Returns the Enum Value that has the given id.
	 *
	 * @param idToGet
	 *            id to check against
	 * @return the found Enum Value
	 */
	public static View valueOf(final int idToGet)
	{
		return View.ID_MAPPING.get(idToGet);
	}
}