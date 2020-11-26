package com.cts;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMqConsumer {
	public static void main(String args[]) throws Exception {
		
		CamelContext camel = new DefaultCamelContext();
		
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
		
		camel.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("activemq:queue:queue1")
				.to("seda:end");
			}
		});
		while(true) {
		camel.start();
		
		ConsumerTemplate consumer = camel.createConsumerTemplate();
		String message = consumer.receiveBody("seda:end" , String.class);
		
		System.out.println(message);
		}
		
	}


}