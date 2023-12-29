package com.gi.gateway.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class MyRabbitConfig extends MyRabbitProperties {
	@Bean
	public Declarables directBindings() {
		TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);

		Queue topicQueue1 = new Queue(TOPIC_QUEUE_1, false);

		Binding topicBinding1 = topicBinding(topicQueue1, topicExchange, ROUTING_KEY_1);

		return new Declarables(topicExchange, topicBinding1);
	}

	private Binding topicBinding(Queue queue, TopicExchange exchange, String routingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}
}
