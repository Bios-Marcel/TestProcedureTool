package com.msc.tpt;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.msc.tpt.view.View;
import com.msc.tpt.view.ViewController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Handles non-viewspecific UI Actions.
 *
 * @author Marcel
 * @since 27.10.2017
 */
public final class AppController
{
	private static final Logger			logger		= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final AppController	instance	= new AppController();
	private Stage						primaryStage;

	@FXML private String lastTitle;

	@FXML private Optional<Pane>	currentView	= Optional.empty();
	@FXML private Pane				settingsPane;
	@FXML private Pane				homePane;

	@FXML private StackPane contentPane;

	@FXML private Button		newTestProcedurePlanButton;
	@FXML private MenuButton	openTestProcedurePlanButton;
	@FXML private MenuButton	saveTestProcedurePlanButton;

	@FXML private Label			titleLabel;
	@FXML private ToggleButton	settingsButton;

	/**
	 * @return the Singleton instance of {@link AppController}
	 */
	public static AppController getInstance()
	{
		return instance;
	}

	/**
	 * Sets the scenes {@link EventHandler} for {@link KeyEvent}, resulting in the former
	 * {@link EventHandler} to be removed.
	 *
	 * @param keyEventHandler the new handler
	 */
	public void setGlobalKeyListener(final EventHandler<KeyEvent> keyEventHandler)
	{
		primaryStage.getScene().setOnKeyPressed(keyEventHandler);
	}

	/**
	 * Sets the primary state for furhter interaction.
	 *
	 * @param stage
	 *            {@link Stage} that will be set
	 */
	public final void setStage(final Stage stage)
	{
		primaryStage = stage;
	}

	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	/**
	 * Initially launches the GUI.
	 */
	public final void start()
	{
		logger.info("Loading view mainview.");

		final FXMLLoader loader = new FXMLLoader();
		loader.setResources(Main.getLanguage());

		try
		{
			loader.setController(this);
			loader.setLocation(AppController.class.getResource("/com/msc/tpt/fxml/Main.fxml"));

			final Parent toLoad = loader.load();
			toLoad.getStylesheets().setAll("/com/msc/tpt/style/application.css");
			primaryStage.getScene().setRoot(toLoad);

			newTestProcedurePlanButton.setText("\uf0fe");
			openTestProcedurePlanButton.setText("\uf07c");
			saveTestProcedurePlanButton.setText("\uf0c7");

			settingsButton.setText("\uf013");

			setTitle(Main.getString("main.title"));
		}
		catch (final IOException exception)
		{
			logger.log(Level.SEVERE, "Error while loading mainview.", exception);
		}

		homePane = getView(View.HOME);
		settingsPane = getView(View.SETTINGS);

		settingsButton.selectedProperty().addListener((observable, oldVal, newVal) -> showSettings(newVal));

		contentPane.getChildren().add(settingsPane);
		contentPane.getChildren().add(homePane);

		primaryStage.show();
	}

	/**
	 * Sets the title in the top bar.
	 *
	 * @param title
	 */
	public void setTitle(final String title)
	{
		lastTitle = titleLabel.getText();
		titleLabel.setText(title);
	}

	/**
	 * Loads a view in the current {@link #primaryStage Stage}.
	 *
	 * @param view
	 *            the view to be loaded
	 */
	public void loadView(final View view)
	{
		logger.info("Loading view: '" + view + "'");

		currentView.ifPresent(viewToRemove -> contentPane.getChildren().remove(viewToRemove));
		currentView = Optional.of(getView(view));
		currentView.ifPresent(viewToAdd ->
		{
			contentPane.getChildren().add(viewToAdd);
			settingsButton.setDisable(false);
			settingsButton.setSelected(false);
		});

	}

	private Pane getView(final View view)
	{
		logger.info("Retrieving view: '" + view + "'");

		final FXMLLoader loader = new FXMLLoader();
		loader.setResources(Main.getLanguage());

		try
		{
			final ViewController controller = view.getControllerType().newInstance();
			loader.setController(controller);
			loader.setLocation(AppController.class.getResource(view.getFXMLPath()));

			final Pane toLoad = loader.load();
			toLoad.getStylesheets().setAll(view.getStylesheetPath());
			return toLoad;
		}
		catch (final IOException | InstantiationException | IllegalAccessException exception)
		{
			logger.log(Level.SEVERE, "Error while loading view: '" + view + "'", exception);
			throw new RuntimeException(exception);
		}
	}

	private void showSettings(final boolean show)
	{
		if (show)
		{
			settingsPane.toFront();
			setTitle(Main.getString("settings.title"));
		}
		else
		{
			settingsPane.toBack();
			currentView.ifPresent(Pane::toFront);
			setTitle(lastTitle);
		}
	}
}