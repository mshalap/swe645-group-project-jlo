package swe645;


import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

import swe645.entities.*;

@Stateless
@Path("survey")
public class StudentSurvey{

	// The address to our KAFKA server
	private static final String KAFKA_SERVER = "184.72.100.51:32710";

	public static boolean isNullOrBlank(String param) { 
	    return param == null || param.trim().length() == 0;
	}

	
	@GET
	@Produces("application/json")
	public List<Survey> get() {
		
		// The Kafka topic name
		String topicName = "survey-data-topic";
		
		Properties consumerProps = new Properties();
		consumerProps.put("bootstrap.servers", KAFKA_SERVER);
		consumerProps.put("group.id", "Angular");				// All of our consumers will just be in the "Angular" group
		consumerProps.put("enable.auto.commit", "false");  		// Prevents offset from getting committed so that we retrieve all messages in the topic each time
		consumerProps.put("auto.commit.interval.ms", "1000");
		consumerProps.put("auto.offset.reset", "earliest");
		consumerProps.put("session.timeout.ms", "30000");
		consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProps);
      
		//Kafka Consumer subscribes list of topics here
		consumer.subscribe(Arrays.asList(topicName));
		
		ObjectMapper mapper = new ObjectMapper();
		java.util.List<Survey> surveyList = new java.util.ArrayList<Survey>();
		
		// Fetch the data with 10 second timeout
		ConsumerRecords<String, String> records = consumer.poll(10000);
		
		for (ConsumerRecord<String, String> record : records)
		{
			Survey s;
			try
			{
				// Try to convert the JSON string representation of each record from Kafka into a Survey object
				s = mapper.readValue(record.value(), Survey.class);
			}
			catch(Exception e)
			{
				// If the string record from Kafka can't be converted to a Survey object, then ignore it. We have
				// some junk test strings committed to our Kafka topic that need to be ignored.
				s = null;
			}

			if (s != null)
			{
				// If the Kafka record was a valid survey, add it to the list to return to the caller.
				surveyList.add(s);
			}
		 
		}
		 
		consumer.close();
		return surveyList;
	}
	
	@POST
	@Consumes("application/json")
	public Response postdata(Survey s){
		try {
			// Check that required fields are filled
			if(isNullOrBlank(s.getFname())|| isNullOrBlank(s.getFname()) || isNullOrBlank(s.getAddr()) || isNullOrBlank(s.getCity()) ||
					isNullOrBlank(s.getState()) || isNullOrBlank(s.getZip()) || isNullOrBlank(s.getTele()) || isNullOrBlank(s.getEmail())) {
				return Response.status(Status.BAD_REQUEST).entity("Required field missing").build();
			}
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			String surveyString = "";
			
			try
			{
				// Try to convert the Survey object the user sent to a JSON string
				surveyString = mapper.writeValueAsString(s);
			}
			catch (Exception e)
			{
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity(e.getMessage()).build();
			}
			
			String topicName = "survey-data-topic";
			
			Properties producerProps = new Properties();
			
			producerProps.put("bootstrap.servers", KAFKA_SERVER);
			producerProps.put("acks", "all");
			producerProps.put("retries", 0);
			producerProps.put("batch.size", 16384);  
			producerProps.put("linger.ms", 1);
			producerProps.put("buffer.memory", 33554432);
			producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

			Producer<String, String> producer = new KafkaProducer<String, String>(producerProps);
	
			try
			{
				// Per Professor Dubey, the easiest way to accomplish FIFO is to always publish the data
				// with the same key. That way all data will go to the same partition. So we are using the key
				// "AngularKey".
				producer.send(new ProducerRecord<String, String>(topicName, "AngularKey", surveyString));
				producer.close();
			}
			catch(Exception e)
			{
				return Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build();
			}
		
		}catch(Exception e) {
			return Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build();
		}
		return Response.created(null).build();
	}
}
