

package com.example.kafkastreams;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler.DeserializationHandlerResponse;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

@Component
public class ProducerHandler extends LogAndContinueExceptionHandler implements  DeserializationExceptionHandler{

	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	

	



	@Override
	public DeserializationHandlerResponse handle(ProcessorContext context, ConsumerRecord<byte[], byte[]> record,
			Exception exception) {
		// TODO Auto-generated method stub
		return DeserializationHandlerResponse.CONTINUE;
	}

}
