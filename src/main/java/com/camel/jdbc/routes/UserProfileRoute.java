package com.camel.jdbc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRoute extends RouteBuilder {

	 @Autowired
	 private Environment env;
	
	@Override
	public void configure() {
		restConfiguration().component("servlet")
		.contextPath("/camel-rest") // application context path.
		.port(env.getProperty("server.port", "8080")) //server port
		.bindingMode(RestBindingMode.off);

		rest("/insert").consumes("text/plain")
		.get("/insert-user").route().from("file://E:/support/praveen/users/?fileName=users.txt&noop=true"). /*("file-route");*/
		

		/*from("file://E:/support/praveen/users/?fileName=users.txt&noop=true").routeId("file-route") //Change file path to desired location
*/		split(body().tokenize("\n"))
    	.streaming()
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String text = exchange.getIn().getBody(String.class);
				String sql = null;
				if(!text.contains("userName")) {
					String[] str = text.split("|");
					 sql = "INSERT INTO user_details('"+str[0]+"','"+str[1]+"','"+str[2]+"')";
				} 
				exchange.getIn().setBody(sql);
			}
		}).to("jdbc:dataSource");
	}
}
