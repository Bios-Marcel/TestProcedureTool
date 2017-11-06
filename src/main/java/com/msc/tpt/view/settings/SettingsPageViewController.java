package com.msc.tpt.view.settings;

import com.msc.tpt.view.ViewController;

/**
 * Contains methods for the {@link SettingsController} to communicate with its pages.
 *
 * @author Marcel
 * @since 04.11.2017
 */
public interface SettingsPageViewController extends ViewController
{
	/**
	 * @return The visible title of the view which thsi controller handles
	 */
	String getTitle();
}
