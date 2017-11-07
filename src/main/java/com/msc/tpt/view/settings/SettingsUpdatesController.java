package com.msc.tpt.view.settings;

import java.util.logging.Logger;

import com.msc.tpt.Main;
import com.msc.tpt.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

/**
 * Cotroller for the <b>General</b> page of the settings.
 *
 * @author Marcel
 * @since 04.11.2017
 */
public class SettingsUpdatesController implements ViewController
{
  private static final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  @FXML
  private CheckBox automaticUpdatesCheckBox;

  @Override
  public void initialize()
  {
    logger.info( "Initializing Update Settings page" );

    automaticUpdatesCheckBox.setSelected( Main.settingsInstance.getAutomaticUpdatesEnabled() );
    automaticUpdatesCheckBox.selectedProperty().addListener( ( observable, oldValue, newValue ) ->
    {
      Main.settingsInstance.setAutomaticUpdatesEnabled( newValue );
      Main.saveSettings();
    } );
  }

  @Override
  public void close()
  {
    // DO smth
  }

  @Override
  public void pastInitialize()
  {
    // Do Nothing (Rethink this)
  }
}
