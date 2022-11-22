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
public class ExceptionHandler implements StreamsUncaughtExceptionHandler{//implements DeserializationExceptionHandler{

	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public StreamThreadExceptionResponse handle(Throwable exception) {
		// TODO Auto-generated method stub
		System.out.println("error");
		return StreamThreadExceptionResponse.REPLACE_THREAD;
	}

}
