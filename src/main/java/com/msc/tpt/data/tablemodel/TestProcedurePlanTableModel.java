package com.msc.tpt.data.tablemodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestProcedurePlanTableModel
{
  private final StringProperty  nameProperty      = new SimpleStringProperty( "" );
  private final StringProperty  procedureProperty = new SimpleStringProperty( "" );
  private final IntegerProperty numberProperty    = new SimpleIntegerProperty( 1 );
  private final StringProperty  uuidProperty      = new SimpleStringProperty( "" );

  public StringProperty procedureProperty()
  {
    return procedureProperty;
  }

  public StringProperty nameProperty()
  {
    return nameProperty;
  }

  /**
   * @return the numberProperty
   */
  public IntegerProperty numberProperty()
  {
    return numberProperty;
  }

  /**
   * @return the uuidProperty
   */
  public StringProperty uuidProperty()
  {
    return uuidProperty;
  }
}
