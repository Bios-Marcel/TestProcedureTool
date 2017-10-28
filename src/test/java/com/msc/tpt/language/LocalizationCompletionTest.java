package com.msc.tpt.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.msc.tpt.constants.PathConstants;

/**
 * This class is supposed to test if all localization files at least contain every key which the
 * english one, which is the main one, contains.
 *
 * @author Marcel
 * @since 28.10.2017
 */
public class LocalizationCompletionTest
{
	@SuppressWarnings("javadoc")
	@Test
	public void testForCompletion()
	{
		// Load language files
		final Locale localeEn = new Locale("en");
		final ResourceBundle english = ResourceBundle.getBundle(PathConstants.LANGUAGE, localeEn);

		final Locale localeDe = new Locale("de");
		final ResourceBundle german = ResourceBundle.getBundle(PathConstants.LANGUAGE, localeDe);

		final Enumeration<String> englishKeys = english.getKeys();
		while (englishKeys.hasMoreElements())
		{
			final String englishKey = englishKeys.nextElement();
			assertTrue(german.containsKey(englishKey), "German localization file doesn't contain key '" + englishKey + "'.");
		}
	}
}
