package com.msc.tpt.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msc.tpt.data.tablemodel.TestProcedurePlanTableModel;

public class TestProcedureStep
{
  /**
   * The uuid is used to be able to tell what {@link TestProcedureStep} an executed Step in a
   * {@link TestProcedureResult} belongs to.
   */
  @JsonIgnore
  private final String                  uuid;
  /**
   * The name, which is used to enable humans to be able to recognize a test.
   */
  private String                        name;
  /**
   * Contains all substeps.
   */
  private final List<TestProcedureStep> subSteps = new ArrayList<>();

  public TestProcedureStep( final @JsonProperty( "uuid" ) String uuid )
  {
    this.uuid = uuid;
  }

  /**
   * @return the uuid
   */
  public String getUuid()
  {
    return uuid;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }


  /**
   * @param name the name to set
   */
  public void setName( final String name )
  {
    this.name = name;
  }

  /**
   * Adds a {@link TestProcedureStep substep} to the existing {@link TestProcedureStep}.
   *
   * @param subStep {@link TestProcedureStep} to be added as a substep
   */
  public void addSubStep( final TestProcedureStep subStep )
  {
    subSteps.add( subStep );
  }

  /**
   * @return {@link #subSteps}
   */
  public List<TestProcedureStep> getSubSteps()
  {
    return subSteps;
  }

  public TestProcedurePlanTableModel toModel()
  {
    final TestProcedurePlanTableModel model = new TestProcedurePlanTableModel();
    model.nameProperty().set( getName() );
    model.nameProperty().set( getName() );
    return model;
  }

}
