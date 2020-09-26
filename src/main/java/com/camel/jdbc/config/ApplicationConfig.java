package com.camel.jdbc.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public ServletRegistrationBean<CamelHttpTransportServlet> camelServletRegistrationBean() {
		ServletRegistrationBean<CamelHttpTransportServlet> registration = new ServletRegistrationBean<CamelHttpTransportServlet>(
				new CamelHttpTransportServlet(), "/*");
		registration.setName("CamelServlet");
		return registration;
	}
}
