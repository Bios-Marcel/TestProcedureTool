package com.msc.tpt.view;

import java.util.logging.Logger;

import com.msc.tpt.AppController;
import com.msc.tpt.Main;
import com.msc.tpt.data.tablemodel.TestProcedurePlanTableModel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class CreateNewController implements ViewController
{
	private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@FXML private Button									newStepButton;
	@FXML private TableView<TestProcedurePlanTableModel>	tableView;

	@Override
	public void initialize()
	{
		logger.info("Initializing createnew view.");

		final AppController appController = AppController.getInstance();

		appController.setGlobalKeyListener(keyEvent ->
		{
			switch (keyEvent.getCode())
			{
				case F1:
					createNewStep();
					break;
				// $CASES-OMITTED$
				default:
					break;
			}
		});

		appController.setTitle(Main.getString("new.tpp.name"));

		// TODO(MSC) Improve; Button and mnemonic might be displayed better
		newStepButton.setText("\uf067 Step (F1)");
	}

	@FXML
	private void createNewStep()
	{
		final TestProcedurePlanTableModel step = new TestProcedurePlanTableModel();
		step.procedureProperty().set("Fill in");
		step.nameProperty().set("Fill in");
		tableView.getItems().add(step);
	}

	@Override
	public void close()
	{
		// TODO
	}
}
