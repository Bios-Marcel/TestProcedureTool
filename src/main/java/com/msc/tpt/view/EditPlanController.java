package com.msc.tpt.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msc.tpt.AppController;
import com.msc.tpt.Main;
import com.msc.tpt.data.TestProcedurePlan;
import com.msc.tpt.data.tablemodel.TestProcedurePlanTableModel;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditPlanController implements ViewController
{
  private final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  @FXML
  private Button                                     newStepButton;
  @FXML
  private TreeTableView<TestProcedurePlanTableModel> treeTableView;

  /**
   * The {@link #treeTableView} root node; This node won't be displayed and is not relevant for the data.
   */
  private TreeItem<TestProcedurePlanTableModel> rootNode;

  @FXML
  private TreeTableColumn<TestProcedurePlanTableModel, String> nameColumn;

  @Override
  public void initialize()
  {
    logger.info( "Initializing createnew view." );

    final AppController appController = AppController.getInstance();

    appController.setGlobalKeyListener( keyEvent ->
    {
      if ( keyEvent.getCode() == KeyCode.F1 )
      {
        createNewStep();
      }
    } );

    appController.setTitle( Main.getString( "new.tpp.name" ) );

    treeTableView.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
    treeTableView.setTreeColumn( nameColumn );

    final EventHandler<? super KeyEvent> keyEventHandler = keyTyped ->
    {
      if ( keyTyped.isAltDown() )
      {
        if ( !treeTableView.getSelectionModel().getSelectedIndices().isEmpty() )
        {
          if ( keyTyped.getCode() == KeyCode.RIGHT )
          {
            moveRows( true );
          }
          else if ( keyTyped.getCode() == KeyCode.LEFT )
          {
            moveRows( false );
          }
        }
      }
    };

    treeTableView.setOnKeyPressed( keyEventHandler );

    final TestProcedurePlanTableModel rootNodeData = new TestProcedurePlanTableModel();
    rootNodeData.nameProperty().set( Main.getString( "new.tpp.name" ) );

    rootNode = new TreeItem<>( rootNodeData );
    treeTableView.setRoot( rootNode );

    loadFile( "D:" + File.separator + "test.json" );

    // TODO(MSC) Improve; Button and mnemonic might be displayed better
    newStepButton.setText( "\uf067 Step (F1)" );
  }

  private void loadFile( final String path )
  {
    final ObjectMapper mapper = new ObjectMapper();
    try
    {
      rootNode.getChildren().clear();
      final TestProcedurePlan plan = mapper.readValue( new File( path ), TestProcedurePlan.class );
      plan.getSteps().forEach( step ->
      {
        final TestProcedurePlanTableModel data = new TestProcedurePlanTableModel();
        data.nameProperty().set( plan.getName() );
        data.uuidProperty().set( plan.getUuid() );
        rootNode.getChildren().add( new TreeItem<>( data ) );
      } );
    }
    catch ( final IOException e )
    {
      e.printStackTrace();
      // TODO(msc|07.11.2017): Fehlerbehandlung muss noch implementiert werden!
    }
  }

  private void moveRows( final boolean moveRight )
  {

    final TreeTableViewSelectionModel<TestProcedurePlanTableModel> selectionModel = treeTableView.getSelectionModel();
    final List<TreeItem<TestProcedurePlanTableModel>> selectedItems = selectionModel
        .getSelectedItems()
        .stream()
        .distinct()
        .collect( Collectors.toList() );

    final int numberOfItems = selectedItems.size();

    final TreeItem<TestProcedurePlanTableModel> firstSelectedItem = selectedItems.get( 0 );
    final TreeItem<TestProcedurePlanTableModel> parentOfFirstItem = firstSelectedItem.getParent();
    final long numberOfLeftOverItems = selectedItems.stream()
        .filter( item -> item.getParent().equals( parentOfFirstItem ) )
        .count();

    if ( numberOfItems == numberOfLeftOverItems )
    {//Only if all selected items have the same parent they will be moved
      final int treeLevel = treeTableView.getTreeItemLevel( firstSelectedItem );

      if ( treeLevel < 2 && !moveRight )
      {//If the item will be moved to the left and the tree level is 1 or less, we won't move.
        return;
      }

      final TreeItem<TestProcedurePlanTableModel> newParentItem;
      if ( moveRight )
      {
        newParentItem = firstSelectedItem.previousSibling();
        if ( Objects.isNull( newParentItem ) )
        {//If there is no previous sibling, we won't move
          return;
        }
      }
      else
      {//move left
        newParentItem = firstSelectedItem.getParent().getParent();
      }

      /* HACK(MSC) This has to happen before removing / adding the items, because otherwise cleaing the selection
       * will lead to weird bugs later on. */
      selectionModel.clearSelection();
      selectedItems.stream().filter( Objects::nonNull ).forEach( item ->
      {
        final TreeItem<TestProcedurePlanTableModel> oldParent = item.getParent();
        oldParent.getChildren().remove( item );

        if ( !moveRight )
        {
          final int oldParentIndex = oldParent.getParent().getChildren().indexOf( oldParent );
          final int newIndex = oldParentIndex + 1 + selectedItems.indexOf( item );
          newParentItem.getChildren().add( newIndex, item );
        }
        else
        {
          newParentItem.getChildren().add( item );
        }
      } );


      treeTableView.refresh();
      newParentItem.setExpanded( true );
      Platform.runLater( () ->
      {
        //Clearing selection again to make sure its fine.
        selectionModel.clearSelection();
        selectedItems.forEach( selectionModel::select );
      } );
    }

  }


  @FXML
  private void createNewStep()
  {
    final TestProcedurePlanTableModel step = new TestProcedurePlanTableModel();
    final int number = rootNode.getChildren().size();
    step.numberProperty().set( number + 1 );
    rootNode.getChildren().add( new TreeItem<>( step ) );
  }

  @Override
  public void close()
  {
    // TODO
  }

  @Override
  public void pastInitialize()
  {
    // TODO Rethink this
  }
}
