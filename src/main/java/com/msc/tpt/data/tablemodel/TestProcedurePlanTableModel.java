package com.msc.tpt.data.tablemodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestProcedurePlanTableModel
{
	private final StringProperty	nameProperty		= new SimpleStringProperty();
	private final StringProperty	procedureProperty	= new SimpleStringProperty();

	public StringProperty procedureProperty()
	{
		return procedureProperty;
	}

	public StringProperty nameProperty()
	{
		return nameProperty;
	}
}
