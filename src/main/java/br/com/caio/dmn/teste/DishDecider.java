package br.com.caio.dmn.teste;

import java.io.IOException;
import java.io.InputStream;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class DishDecider {

	public static void main(String[] args) {
		String season = "Spring";

		Integer guestCount = 4;

		// prepare variables for decision evaluation
		VariableMap variables = Variables.putValue("season", season).putValue("guestCount", guestCount);

		// create a new default DMN engine
		DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

		// parse decision from resource input stream
		InputStream inputStream = DishDecider.class.getResourceAsStream("/dish-decision.dmn11.xml");

		try {
			DmnDecision decision = dmnEngine.parseDecision("decision", inputStream);

			// evaluate decision
			DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

			// print result
			String desiredDish = result.getSingleResult().getSingleEntry();
			System.out.println("Dish Decision:\n\tI would recommend to serve: " + desiredDish);

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.err.println("Could not close stream: " + e.getMessage());
			}
		}
	}

}