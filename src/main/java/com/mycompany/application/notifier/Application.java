package com.mycompany.application.notifier;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.mycompany.sdk.redis.AbstractRedisQueue;
import com.mycompany.service.product_review.queue_processor.ArchivedQueueProcessor;
import com.mycompany.service.product_review.queue_processor.PublishedQueueProcessor;

@SpringBootApplication
@EnableJpaRepositories("com.mycompany.model")
@EntityScan("com.mycompany.model")
@ComponentScan("com.mycompany")
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory,
			PublishedQueueProcessor publishedQueueProcessor, AbstractRedisQueue publishedQueue,
			ArchivedQueueProcessor archivedQueueProcessor, AbstractRedisQueue archivedQueue) throws SQLException
	{
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		
		container.addMessageListener(publishedQueueProcessor, publishedQueue.getChannelTopic());
		container.addMessageListener(archivedQueueProcessor, archivedQueue.getChannelTopic());

		dataSource.getConnection().prepareCall("select * from production.product").executeQuery();
		System.out.println("dataSource is online");
		return container;
	}
}
