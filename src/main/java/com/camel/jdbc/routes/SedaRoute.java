package com.camel.jdbc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SedaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("seda:dynamicRoute").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String text = exchange.getIn().getBody(String.class);
				if(!StringUtils.isEmpty(text)) {
					exchange.getIn().setBody(text);
					System.out.println(text);
				}
			}
		}).to("jdbc:dataSource");
	}

}
