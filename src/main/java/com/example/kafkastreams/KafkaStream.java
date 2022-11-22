package com.example.kafkastreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStream {

	@Autowired
	RetryTemplate retryTemplate;
	
	
	StreamsBuilder streamsBuilder;
	
	@Autowired
	Messagejoiner joiner;
	@Autowired
	public void build(StreamsBuilder streamsBuilder) {
	/*	KStream<String,String> messageStream = streamsBuilder
				   .stream("test", Consumed.with(Serdes.String(), Serdes.String())).map((k,v)-> {return new KeyValue<>(v, v);} );
		KTable<String,String> table = messageStream
				.map((k,v)->KeyValue.pair(v, v)).mapValues(v->{return v;})
		  .toTable();
		
		System.out.println("asdsd");*/
		 final KStream<String, String> messageStream = streamsBuilder.stream("test", Consumed.with(Serdes.String(), Serdes.String())).map((k,record)->KeyValue.pair(record, record));
		    // this line takes the previous KStream and converts it to a KTable
		    final KTable<String, String> table = messageStream.toTable(Materialized.with(Serdes.String(), Serdes.String()));
		messageStream.join(table, joiner).mapValues(record ->{
			System.out.println(record);
			return record;
		});
	}
	//@Autowired
	public void buildStream(StreamsBuilder streamsBuilder) {
		
		KStream<String,String> messageStream = streamsBuilder
				   .stream("test", Consumed.with(Serdes.String(), Serdes.String()));
		
		messageStream.filter((key, record)-> {
			System.out.println(record);
			/*retryTemplate.execute(arg0 -> {
				try {
					if(record.toString().equalsIgnoreCase ("1")) {
						throw new NullPointerException();	
					}	
				}catch(Exception ex) {
					System.err.println(ex);
				}
				    return null;
				});*/
			
			if(record!=null) 
				return true;
			return false;
			
		}).mapValues(record ->{
			try {
				System.out.println(record);
				System.out.println(getUpperCase(record.toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return record;
		});
	}
	
	public void retrymethod() {
		retryTemplate.execute(arg0 -> {
		   // myService.templateRetryService();
		    return null;
		});
	}
	
	
	public String getUpperCase(String record) throws Exception{
		return record.toUpperCase();
	}
}
