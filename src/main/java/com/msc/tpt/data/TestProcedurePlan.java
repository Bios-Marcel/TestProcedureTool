package com.msc.tpt.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link TestProcedurePlan} contains meta info about the plan and the steps.
 *
 * @author Marcel
 * @since 28.10.2017
 */
public class TestProcedurePlan
{
	/**
	 * The uuid is used to be able to tell what {@link TestProcedurePlan} a
	 * {@link TestProcedureResult} belongs to.
	 */
	@JsonIgnore private final String uuid;

	/**
	 * To be used for humans to identify the {@link TestProcedurePlan}.
	 */
	private String							name;
	/**
	 * Contains all tests, whcih a tester has to / can execute.
	 */
	private final List<TestProcedureStep>	steps	= new ArrayList<>();

	/**
	 * Initializes a TestProcedurePlan.
	 *
	 * @param uuid to be set as the uuid
	 */
	public TestProcedurePlan(final @JsonProperty("uuid") String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return {@link #uuid}
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @return {@link #name}
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the {@link TestProcedurePlan} name.
	 *
	 * @param name name to be set
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * Adds a {@link TestProcedureStep} to the {@link TestProcedurePlan} {@link #steps tsetsteps}.
	 *
	 * @param step {@link TestProcedureStep} to be added
	 */
	public void addStep(final TestProcedureStep step)
	{
		steps.add(step);
	}

	/**
	 * @return {@link #steps}
	 */
	public List<TestProcedureStep> getSteps()
	{
		return steps;
	}
}
