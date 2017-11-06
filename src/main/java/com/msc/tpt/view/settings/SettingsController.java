package com.msc.tpt.view.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.msc.tpt.fxutil.FXUtil;
import com.msc.tpt.view.ViewController;

import javafx.beans.binding.Bindings;
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
	@FXML private ListView<Node>	settingPageListView;
	@FXML private StackPane			settingPageContainer;
	@FXML private TextField			settingFilterTextField;

	private final Map<Node, List<String>> searchWords = new HashMap<>();

	@FXML private Label titleLabel;

	private final ObservableList<Node>	nodes			= FXCollections.observableArrayList();
	private final FilteredList<Node>	filteredNodes	= new FilteredList<>(nodes);

	@Override
	public void initialize()
	{
		System.out.println("Initializing");

		settingFilterTextField.textProperty().addListener((observable, oldValue, newValue) ->
		{
			filteredNodes.predicateProperty().set(node ->
			{
				final String searchTerm = newValue.replaceAll("\\s", "");
				if (searchTerm.isEmpty())
				{
					return true;
				}

				final List<String> filterWords = searchWords.get(node);

				for (final String word : filterWords)
				{
					if (word.toLowerCase().contains(searchTerm))
					{
						return true;
					}
				}

				return false;
			});
		});

		settingPageListView.setCellFactory(param -> new ListCell<Node>() {
			@Override
			protected void updateItem(final Node item, final boolean empty)
			{
				super.updateItem(item, empty);

				if (Objects.nonNull(item))
				{
					setText(((SettingsGeneralController) item.getUserData()).getTitle());
				}
				else
				{
					setText("");
				}
			}
		});

		Bindings.bindContent(settingPageContainer.getChildren(), nodes);

		settingPageListView.setItems(filteredNodes);
		settingPageListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
		{
			if (Objects.nonNull(newValue))
			{
				titleLabel.setText(((SettingsGeneralController) newValue.getUserData()).getTitle());
				newValue.toFront();
			}
		});

		final Pane generalPane = loadPane(new SettingsGeneralController(), "/com/msc/tpt/fxml/SettingsGeneral.fxml");
		nodes.add(generalPane);
		settingPageListView.getSelectionModel().select(0);
	}

	@Override
	public void pastInitialize()
	{
		nodes.forEach(node ->
		{
			final String pageTitle = ((SettingsGeneralController) node.getUserData()).getTitle();

			final List<String> words = new ArrayList<>();
			addToSearchWords(node, words::add);
			words.add(pageTitle);
			searchWords.put(node, words);
		});
	}

	private static void addToSearchWords(final Node node, final Consumer<String> consumer)
	{
		if (node instanceof Pane)
		{
			final Pane parent = (Pane) node;
			for (final Node child : parent.getChildrenUnmodifiable())
			{
				addToSearchWords(child, consumer);
			}
		}
		else if (node instanceof Label)
		{
			final Label label = (Label) node;
			final String adding = label.getText();
			consumer.accept(adding);
		}
	}

	private Pane loadPane(final SettingsPageViewController controller, final String fxmlLocation)
	{
		final Pane pane = FXUtil.loadFXML(controller, SettingsController.class.getResource(fxmlLocation));
		pane.setUserData(controller);
		return pane;
	}

	@Override
	public void close()
	{
		// Do smth
	}

}
