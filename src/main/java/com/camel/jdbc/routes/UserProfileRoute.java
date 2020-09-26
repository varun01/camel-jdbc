package com.camel.jdbc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRoute extends RouteBuilder {

	@Override
	public void configure() {
/*		restConfiguration().component("servlet")
		.contextPath("/camel-rest") // application context path.
		.port(env.getProperty("server.port", "8080")) //server port
		.bindingMode(RestBindingMode.off);*/
		restConfiguration()
		.component("servlet")
		.bindingMode(RestBindingMode.off);

		rest("/insert").get("/insert-user").route().transform()
				.simple("Hello World!!").endRest().to("direct:insert");
		
		
		
		from("direct:insert").log(LoggingLevel.INFO, "Called from insert route")
				.from("file://E:/support/praveen/users?fileName=users.txt&noop=true")
				.log(LoggingLevel.INFO, "Calling from file route").routeId("insert-users").split(body().tokenize("\n"))
				.streaming().process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						String text = exchange.getIn().getBody(String.class);
						String sql = null;
						if (!text.contains("userName")) {
							String[] str = text.split("\\|");
							sql = "INSERT INTO user_details values('" + str[0].trim() + "','" + str[1].trim() + "','" + str[2].trim() + "')";
							exchange.getIn().setBody(sql);
							/*System.out.println(sql);*/
						}
					}
				}).to("jdbc:dataSource");
	}
}
