package com.lambda.demo.serverless;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class Hello implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	StringBuffer finalString = new StringBuffer("Hello! Reached the Spring Cloud Function ");
    @Override
	public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
		
		if (input != null && input.getQueryStringParameters() != null) {
			Map<String, String> hm = input.getQueryStringParameters();
			if (hm.keySet().contains("Add") && hm.keySet().contains("parameteradd1")
					&& hm.keySet().contains("parameteradd2")) {
				ValueExtractionAndAction add = (s1, s2) -> (" " + "Addition" + " " + s1 + "+" + s2 + "= " + (Integer.parseInt(s1) + Integer.parseInt(s2)));
				finalString.append(add.action(hm.get("parameteradd1"), hm.get("parameteradd2")));
			}
			if (hm.keySet().contains("Sub") & hm.keySet().contains("parametersub1")
					&& hm.keySet().contains("parametersub2")) {
				ValueExtractionAndAction add = (s1, s2) -> (" " + "Substraction" + " " + s1 + "-" + s2 + "= " + (Integer.parseInt(s1) - Integer.parseInt(s2)));
				finalString.append(add.action(hm.get("parametersub1"), hm.get("parametersub2")));
			}
			if (hm.keySet().contains("Multiply") & hm.keySet().contains("parametermulti1")
					&& hm.keySet().contains("parametermulti2")) {
				ValueExtractionAndAction add = (s1, s2) -> (" " + "Multiplication" + " " + s1 + "x" + s2 + "= " + (Integer.parseInt(s1) * Integer.parseInt(s2)));
				finalString.append(add.action(hm.get("parametermulti1"), hm.get("parametermulti2")));
			}
		}
		responseEvent.setStatusCode(200);
		responseEvent.setBody(finalString.toString());
		return responseEvent;
	}

	@FunctionalInterface
	interface ValueExtractionAndAction {
		String action(String s1, String s2);
	}
}
