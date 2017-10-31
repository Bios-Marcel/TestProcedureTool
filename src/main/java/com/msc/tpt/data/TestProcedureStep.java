package com.msc.tpt.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestProcedureStep
{
	/**
	 * The uuid is used to be able to tell what {@link TestProcedureStep} an executed Step in a
	 * {@link TestProcedureResult} belongs to.
	 */
	@JsonIgnore private final String	uuid;
	private String						name;

	public TestProcedureStep(final @JsonProperty("uuid") String uuid)
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

	public void setName(final String name)
	{
		this.name = name;
	}
}
