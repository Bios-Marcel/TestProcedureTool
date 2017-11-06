package com.msc.tpt.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestProcedureStep
{
  /**
   * The uuid is used to be able to tell what {@link TestProcedureStep} an executed Step in a
   * {@link TestProcedureResult} belongs to.
   */
  @JsonIgnore
  private final String uuid;
  private String       name;

  /**
   * Contains all substeps.
   */
  private final List<TestProcedureStep> subSteps = new ArrayList<>();

  public TestProcedureStep( final @JsonProperty( "uuid" ) String uuid )
  {
    this.uuid = uuid;
  }

  public String getUuid()
  {
    return uuid;
  }

  public String getName()
  {
    return name;
  }

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
}
