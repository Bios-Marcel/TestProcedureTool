package com.msc.tpt.view;

/**
 * Describes what FXMl ViewControllers have to look like.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public interface ViewController
{
	/**
	 * Automatically called when initializing the controller.
	 */
	public void initialize();

	/**
	 * Called when the view is being closed.
	 */
	public void close();

	/**
	 * Executed after the fxmloader is done.
	 */
	public void pastInitialize();
}
