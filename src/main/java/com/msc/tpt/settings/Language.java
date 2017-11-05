package com.msc.tpt.settings;

import java.util.Locale;

public enum Language
{
	en,
	de;

	public Locale toLocale()
	{
		return new Locale(this.toString());
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}
