package com.msc.tpt.settings;

import java.util.Locale;

/**
 * Persistable Datamodel for settings.
 *
 * @author msc
 * @since 06.11.2017
 *
 */
public class Settings
{
  // Settings
  private Locale  language                = new Locale( "en" );
  private Boolean automaticUpdatesEnabled = true;

  /**
   * @return the language
   */
  public Locale getLanguage()
  {
    return language;
  }

  /**
   * @param language the language to set
   */
  public void setLanguage( final Locale language )
  {
    this.language = language;
  }

  /**
   * @return the automaticUpdatesEnabled
   */
  public Boolean getAutomaticUpdatesEnabled()
  {
    return automaticUpdatesEnabled;
  }

  /**
   * @param automaticUpdatesEnabled the automaticUpdatesEnabled to set
   */
  public void setAutomaticUpdatesEnabled( final Boolean automaticUpdatesEnabled )
  {
    this.automaticUpdatesEnabled = automaticUpdatesEnabled;
  }
}
