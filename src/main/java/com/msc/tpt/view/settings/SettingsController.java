package com.msc.tpt.view.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.msc.tpt.Main;
import com.msc.tpt.fxutil.FXUtil;
import com.msc.tpt.settings.SettingsPage;
import com.msc.tpt.view.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SettingsController implements ViewController
{
  private static final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  @FXML
  private ListView<SettingsPage> settingPageListView;
  @FXML
  private StackPane              settingPageContainer;
  @FXML
  private TextField              settingFilterTextField;

  private final Map<Node, List<String>> searchWords = new HashMap<>();

  @FXML
  private Label titleLabel;

  private final ObservableList<SettingsPage> nodes         = FXCollections.observableArrayList();
  private final FilteredList<SettingsPage>   filteredNodes = new FilteredList<>( nodes );

  @Override
  public void initialize()
  {
    logger.info( "Initializing Settingscontroller" );

    settingFilterTextField.textProperty().addListener( ( observable, oldValue, newValue ) ->
    {
      filteredNodes.predicateProperty().set( node ->
      {
        final String searchTerm = newValue.replaceAll( "\\s", "" );
        if ( searchTerm.isEmpty() )
        {
          return true;
        }

        final List<String> filterWords = searchWords.get( node.getPageNode() );

        for ( final String word : filterWords )
        {
          if ( word.toLowerCase().contains( searchTerm ) )
          {
            return true;
          }
        }

        return false;
      } );
    } );

    settingPageListView.setCellFactory( param -> new ListCell<SettingsPage>()
    {
      @Override
      protected void updateItem( final SettingsPage item, final boolean empty )
      {
        super.updateItem( item, empty );

        if ( Objects.nonNull( item ) )
        {
          setText( item.getTitle() );
        }
        else
        {
          setText( "" );
        }
      }
    } );

    settingPageListView.setItems( filteredNodes );
    settingPageListView.getSelectionModel().selectedItemProperty().addListener( ( observable, oldValue, newValue ) ->
    {
      if ( Objects.nonNull( newValue ) )
      {
        titleLabel.setText( newValue.getTitle() );
        settingPageContainer.getChildren().setAll( newValue.getPageNode() );
      }
    } );

    final Pane generalPane = loadPane( new SettingsGeneralController(), "/com/msc/tpt/fxml/SettingsGeneral.fxml" );
    nodes.add( new SettingsPage( generalPane, Main.getString( "settings.page.general.title" ) ) );

    final Pane updatePane = loadPane( new SettingsUpdatesController(), "/com/msc/tpt/fxml/SettingsUpdates.fxml" );
    nodes.add( new SettingsPage( updatePane, Main.getString( "settings.page.updates.title" ) ) );

    settingPageListView.getSelectionModel().select( 0 );
  }

  @Override
  public void pastInitialize()
  {
    nodes.forEach( page ->
    {
      final List<String> words = new ArrayList<>();
      final Node node = page.getPageNode();
      addToSearchWords( node, words::add );
      words.add( page.getTitle() );
      searchWords.put( node, words );
    } );
  }

  private static void addToSearchWords( final Node node, final Consumer<String> consumer )
  {
    if ( node instanceof Pane )
    {
      final Pane parent = (Pane) node;
      for ( final Node child : parent.getChildrenUnmodifiable() )
      {
        addToSearchWords( child, consumer );
      }
    }
    else if ( node instanceof Label )
    {
      final Label label = (Label) node;
      final String adding = label.getText();
      consumer.accept( adding );
    }
  }

  private static Pane loadPane( final ViewController controller, final String fxmlLocation )
  {
    return FXUtil.loadFXML( controller, SettingsController.class.getResource( fxmlLocation ) );
  }

  @Override
  public void close()
  {
    //TODO Do something?
  }
}
