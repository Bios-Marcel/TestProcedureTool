package com.msc.tpt.view;

import java.util.Locale;

public class Settings
{
	// Settings
	private Locale language = new Locale("en");

	public Locale getLanguage()
	{
		return language;
	}

	public void setLanguage(final Locale language)
	{
		this.language = language;
	}
}
