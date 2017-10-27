package com.msc.tpt.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.msc.tpt.logging.view.UncaughtExceptionDialog;

/**
 * Catches uncaught exceptions, prints the exception and shows them in a dialog.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public class TPTUncaughtExceptionHandler implements UncaughtExceptionHandler
{
	private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void uncaughtException(final Thread thread, final Throwable exception)
	{
		logger.log(Level.SEVERE, exception.getMessage(), exception);

		final UncaughtExceptionDialog dialog = new UncaughtExceptionDialog();
		dialog.setTitle("There has been a problem.");
		dialog.setHeaderText("Uncaught " + exception.getClass().getSimpleName() + " on thread " + thread.getName() + ".");

		final StringWriter sw = new StringWriter();
		try (final PrintWriter pw = new PrintWriter(sw);)
		{
			exception.printStackTrace(pw); // Prints the stacktrace to the printwriter
			final String stackTraceAsString = sw.toString();
			dialog.setStackTrace(exception.getMessage() + System.lineSeparator() + System.lineSeparator() + stackTraceAsString);
		}

		dialog.show();
	}

}
