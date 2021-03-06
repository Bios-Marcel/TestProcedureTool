package com.msc.tpt.logging.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.github.GitHub;

import com.msc.tpt.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates a dialog containing a title, a header text and a textarea to be used
 * for stack traces.
 * This class uses no dependencies besides the logger, to assure it can be used
 * under almost all
 * circumstances.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public class UncaughtExceptionDialog
{
  private final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  private final Stage stage;

  @FXML
  private Label    titleLable;
  @FXML
  private Label    headerTextLabel;
  @FXML
  private TextArea stackTraceTextArea;

  /**
   * Initializes the dialos stage, scene and view.
   */
  public UncaughtExceptionDialog()
  {
    final FXMLLoader loader = new FXMLLoader();
    loader.setController( this );

    stage = new Stage();
    stage.initModality( Modality.APPLICATION_MODAL );
    stage.setTitle( "Uncaught Exception" );

    try
    {
      loader.setLocation( UncaughtExceptionDialog.class.getResource( "/com/msc/tpt/fxml/ExceptionDialog.fxml" ) );
      loader.setResources( Main.getLanguage() );
      stage.setScene( new Scene( loader.load() ) );
    }
    catch ( final IOException exception )
    {
      logger.log( Level.SEVERE, exception.getMessage(), exception );
      System.exit( 0 );
    }
  }

  @FXML
  private void reportBug()
  {
    try
    {
      GitHub.connect( "testproceduretool", "testproctool1" )
          .getRepository( "Bios-Marcel/TestProcedureTool" )
          .createIssue( "Automatically created issue: " + headerTextLabel.getText() )
          .body( stackTraceTextArea.getText() ).create();
    }
    catch ( final IOException exception )
    {
      logger.log( Level.SEVERE, "Error reporting bug on GitHub", exception );
    }
  }

  @FXML
  private void closeDialog()
  {
    stage.close();
  }

  /**
   * Fils the scrollable text error with text. Should be used for the stacktrace.
   *
   * @param text
   *          String to be set as the stacktrace
   */
  public void setStackTrace( final String text )
  {
    stackTraceTextArea.setText( text );
  }

  /**
   * Sets the header text of the exception dialog. The header text is right
   * beneath the title.
   *
   * @param text
   *          String to be set as the header text
   */
  public void setHeaderText( final String text )
  {
    headerTextLabel.setText( text );
  }

  /**
   * Sets the title of the exception dialog.
   *
   * @param text
   *          String to be set as the title
   */
  public void setTitle( final String text )
  {
    titleLable.setText( text );
  }

  /**
   * Shows the dialog.
   */
  public void show()
  {
    stage.show();
    stage.toFront();
  }
}
