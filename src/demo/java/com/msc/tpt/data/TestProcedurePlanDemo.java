package com.msc.tpt.data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the data model, converting it to json and back to a pojo.
 *
 * @author Marcel
 * @since 28.10.2017
 */
public class TestProcedurePlanDemo
{
	/**
	 * The mainpath, where you save the files
	 */
	private static final String PATH = "D:/";

	@SuppressWarnings("javadoc")
	public static void main(final String[] args) throws JsonGenerationException, JsonMappingException, IOException
	{
		final ObjectMapper mapper = new ObjectMapper();

		final TestProcedurePlan plan = new TestProcedurePlan("TPP-" + UUID.randomUUID().toString());
		plan.setName("Test");
		final TestProcedureStep testStep = new TestProcedureStep("TPS-" + UUID.randomUUID().toString());
		testStep.setName("TestStep 1");
		final TestProcedureStep testStep2 = new TestProcedureStep("TPS-" + UUID.randomUUID().toString());
		testStep2.setName("TestStep 2");
		plan.addStep(testStep);
		plan.addStep(testStep2);
		mapper.writeValue(new File(PATH + "plan.json"), plan);

		final TestProcedurePlan plan2 = mapper.readValue(new File(PATH + "plan.json"), TestProcedurePlan.class);
		System.out.println("Plan 2 UUID: " + plan2.getUuid());
		System.out.println("Plan 2 Name: " + plan2.getName());
		plan2.getSteps().forEach(step -> System.out.println("Step: " + step.getName()));
	}
}
