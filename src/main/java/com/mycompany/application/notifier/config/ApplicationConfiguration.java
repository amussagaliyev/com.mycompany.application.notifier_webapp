package com.mycompany.application.notifier.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.mycompany.service.product_review.queue_configuration.QueueConfiguration;

@Configuration
@ComponentScan("com.mycompany")
@PropertySource("classpath:application.properties")
@Import(QueueConfiguration.class)
public class ApplicationConfiguration
{
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Autowired
	private MessageListenerAdapter publishedQueueListener;
	@Autowired
	private ChannelTopic publishedQueueTopic;
	
	@Autowired
	private MessageListenerAdapter archivedQueueListener;
	@Autowired
	private ChannelTopic archivedQueueTopic;

	@Bean
	public RedisMessageListenerContainer redisContainer()
	{
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory);
		container.addMessageListener(publishedQueueListener, publishedQueueTopic);
		container.addMessageListener(archivedQueueListener, archivedQueueTopic);
		return container;
	}
	
}