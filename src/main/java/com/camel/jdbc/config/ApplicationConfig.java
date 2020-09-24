package com.camel.jdbc.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public CamelHttpTransportServlet transportServlet() {
		return new CamelHttpTransportServlet();
	}
	
	
}
