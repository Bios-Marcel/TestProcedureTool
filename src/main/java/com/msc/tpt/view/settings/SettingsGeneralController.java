package com.msc.tpt.view.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.msc.tpt.AppController;
import com.msc.tpt.Main;
import com.msc.tpt.settings.Language;
import com.msc.tpt.view.ViewController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Cotroller for the <b>General</b> page of the settings.
 *
 * @author Marcel
 * @since 04.11.2017
 */
public class SettingsGeneralController implements ViewController
{
  private static final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  @FXML
  private ComboBox<Locale> languageComboBox;

  @Override
  public void initialize()
  {
    logger.info( "Initializing General Settings page" );

    final List<Locale> locales = Arrays.asList( Language.values() )
        .stream()
        .map( Language::toLocale )
        .collect( Collectors.toList() );

    languageComboBox.setItems( FXCollections.observableArrayList( locales ) );
    languageComboBox.getSelectionModel().select( Main.settingsInstance.getLanguage() );
    languageComboBox.getSelectionModel().selectedItemProperty().addListener( ( observable, oldValue, newValue ) ->
    {
      Main.settingsInstance.setLanguage( newValue );
      Main.saveSettings();
      AppController.getInstance().getPrimaryStage().hide();
      Main.startApplication();
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
