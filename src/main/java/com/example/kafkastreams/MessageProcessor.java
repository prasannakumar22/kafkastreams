package com.example.kafkastreams;

import java.time.Duration;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

public class MessageProcessor implements Processor<String, String, Void, Void>{

	private KeyValueStore<String, String> kvStore;

	@Override
    public void init(final ProcessorContext<Void, Void> context) {
        context.schedule(Duration.ofSeconds(1), PunctuationType.STREAM_TIME, timestamp -> {
            try (final KeyValueIterator<String, String> iter = kvStore.all()) {
                while (iter.hasNext()) {
                    final KeyValue<String, String> entry = iter.next();
                   // context.forward(new Record<>(entry.key, entry.value.toString(), timestamp));
                }
            }
        });
        kvStore = context.getStateStore("msgs");
    }
    
	@Override
	public void process(Record<String, String> record) {
		try {
			System.out.println(record);
			System.out.println(getUpperCase(record.toString()));	
		}catch(Exception e) {
			
		}
		
	}
	
	public String getUpperCase(String record) throws Exception{
		return record.toUpperCase();
	}

}
