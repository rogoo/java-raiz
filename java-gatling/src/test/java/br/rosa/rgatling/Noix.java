package br.rosa.rgatling;

import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class Noix extends Simulation {

	private HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
			.acceptHeader("application/json");

	private ScenarioBuilder nonReactive = scenario("NonReactive baby")
			.exec(http("nonreactive").get("/nonreactive"));
	private ScenarioBuilder reactive = scenario("Reactive baby")
			.exec(http("reactive").get("/reactive"));

	{
//		setUp(nonReactive.injectOpen(atOnceUsers(500)),
//				reactive.injectOpen(atOnceUsers(500))).protocols(httpProtocol);
		setUp(nonReactive.injectOpen(rampUsers(100).during(20)),
				reactive.injectOpen(rampUsers(100).during(20)))
				.protocols(httpProtocol);
	}
}
