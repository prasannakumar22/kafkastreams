package com.example.kafkastreams;

import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.stereotype.Component;

@Component
public class Messagejoiner implements ValueJoiner<String, String, String> {

	@Override
	public String apply(String value1, String value2) {
		// TODO Auto-generated method stub
		return value1.concat("-").concat(value2);
	}

}
