package com.example.kafkastreams;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@EnableKafkaStreams
@Configuration
public class KafkaStreamsConfig {
  
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
   public KafkaStreamsConfiguration kafkaStreamsConfig(@Value("${kafka.bootstrap-servers}") final String bootstrapServers) {
       Map props = new HashMap<>();
       props.put(APPLICATION_ID_CONFIG, "kafka-streams-demo");
       props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
  props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
  props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, ProducerHandler.class);
  props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
  props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);

 // props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
  //props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/security/tls/kafka.client.truststore.jks");
  //props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "test1234");
  //props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "/etc/security/tls/kafka.client.keystore.jks");
  //props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "test1234");
  //props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "test1234");
       props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
       return new KafkaStreamsConfiguration(props);
   }
	
	@Autowired
	ExceptionHandler exceptionHandler;

	@Bean
	
	    public RetryTemplate retryTemplate() {
	
	        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
	
	       retryPolicy.setMaxAttempts(4);
	
	
	        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
	
	        backOffPolicy.setBackOffPeriod(3000);
	
	      RetryTemplate template = new RetryTemplate();
	
	        template.setRetryPolicy(retryPolicy);
	
	        template.setBackOffPolicy(backOffPolicy);
	
	
	        return template;
	
	    }
	
	@Bean
	public StreamsBuilderFactoryBeanConfigurer streamsCustomizer() {
	    return new StreamsBuilderFactoryBeanConfigurer() {

	        @Override
	        public void configure(StreamsBuilderFactoryBean factoryBean) {;
	            factoryBean.setStreamsUncaughtExceptionHandler(exceptionHandler);
	        }

	        @Override
	        public int getOrder() {
	            return Integer.MAX_VALUE;
	        }

	    };
	}
}