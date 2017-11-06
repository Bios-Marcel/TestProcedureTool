package com.msc.tpt.view;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.msc.tpt.AppController;
import com.msc.tpt.Main;
import com.msc.tpt.data.tablemodel.TestProcedurePlanTableModel;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CreateNewController implements ViewController
{
  private final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

  @FXML
  private Button                                     newStepButton;
  @FXML
  private TreeTableView<TestProcedurePlanTableModel> tableView;

  private TreeItem<TestProcedurePlanTableModel> rootNode;

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

    tableView.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );

    final EventHandler<? super KeyEvent> keyEventHandler = keyTyped ->
    {
      if ( keyTyped.isAltDown() )
      {
        if ( tableView.getSelectionModel().getSelectedIndices().size() >= 1 )
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

    tableView.setOnKeyPressed( keyEventHandler );

    final TestProcedurePlanTableModel rootNodeData = new TestProcedurePlanTableModel();
    rootNodeData.nameProperty().set( Main.getString( "new.tpp.name" ) );

    rootNode = new TreeItem<>( rootNodeData );
    tableView.setRoot( rootNode );
    tableView.setShowRoot( false );


    // TODO(MSC) Improve; Button and mnemonic might be displayed better
    newStepButton.setText( "\uf067 Step (F1)" );
  }

  private void moveRows( final boolean moveRight )
  {
    final TreeTableViewSelectionModel<TestProcedurePlanTableModel> selectionModel = tableView.getSelectionModel();
    final List<TreeItem<TestProcedurePlanTableModel>> selectedItems = selectionModel
        .getSelectedItems()
        .stream()
        .distinct()
        .collect( Collectors.toList() );

    final int numberOfItems = selectedItems.size();

    //    selectedItems.forEach( item -> System.out.println( item.getValue().nameProperty().get() ) );

    final TreeItem<TestProcedurePlanTableModel> firstSelectedItem = selectedItems.get( 0 );
    final TreeItem<TestProcedurePlanTableModel> parentOfFirstItem = firstSelectedItem.getParent();
    final long numberOfLeftOverItems = selectedItems.stream()
        .filter( item -> item.getParent().equals( parentOfFirstItem ) )
        .count();

    if ( numberOfItems == numberOfLeftOverItems )
    {//Only if all selected items have the same parent they will be moved
      final int treeLevel = tableView.getTreeItemLevel( firstSelectedItem );

      if ( treeLevel < 2 && !moveRight )
      {
        return;
      }

      final ObservableList<TreeItem<TestProcedurePlanTableModel>> children = parentOfFirstItem.getChildren();
      final int indexOfSelectedItem = children.indexOf( firstSelectedItem );
      final TreeItem<TestProcedurePlanTableModel> newParentItem;
      if ( moveRight )
      {
        if ( indexOfSelectedItem == 0 )
        {
          return;
        }
        newParentItem = firstSelectedItem.previousSibling();
      }
      else
      {
        newParentItem = firstSelectedItem.getParent().getParent();
      }

      selectedItems.stream().filter( Objects::nonNull ).forEach( item ->
      {
        final TreeItem<TestProcedurePlanTableModel> oldParent = item.getParent();
        final ObservableList<TreeItem<TestProcedurePlanTableModel>> oldParentChildren = oldParent.getChildren();
        oldParentChildren.remove( item );


        if ( !moveRight )
        {
          final int oldParentIndex = oldParent.getParent().getChildren().indexOf( oldParent );
          newParentItem.getChildren().add( oldParentIndex + 1, item );
        }
        else
        {
          newParentItem.getChildren().add( item );
        }

      } );

      newParentItem.setExpanded( true );

      tableView.refresh();
      selectionModel.clearSelection();
      selectedItems.forEach( selectionModel::select );
    }

  }


  @FXML
  private void createNewStep()
  {
    final TestProcedurePlanTableModel step = new TestProcedurePlanTableModel();
    step.procedureProperty().set( "Fill in " + System.currentTimeMillis() );
    step.nameProperty().set( "Fill in " + System.currentTimeMillis() );
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
