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

	public static boolean isNullOrBlank(String param) { 
	    return param == null || param.trim().length() == 0;
	}

	
	@GET
	@Produces("application/json")
	public List<Survey> get() {
		String topicName = "survey-data-topic";
		Properties consumerProps = new Properties();
		
		consumerProps.put("bootstrap.servers", "184.72.100.51:32710");  //"localhost:9092");
		consumerProps.put("group.id", "Angular");
		consumerProps.put("enable.auto.commit", "false");  //prevents offset from getting committed so that we retrieve all messages in the topic each time
		consumerProps.put("auto.commit.interval.ms", "1000");
		consumerProps.put("auto.offset.reset", "earliest");
		consumerProps.put("session.timeout.ms", "30000");
		consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProps);
      
		//Kafka Consumer subscribes list of topics here.
		consumer.subscribe(Arrays.asList(topicName));
		
		ObjectMapper mapper = new ObjectMapper();
		java.util.List<Survey> surveyList = new java.util.ArrayList<Survey>();
		ConsumerRecords<String, String> records = consumer.poll(10000);
		
		for (ConsumerRecord<String, String> record : records)
		{
			Survey s;
			try
			{
				s = mapper.readValue(record.value(), Survey.class);
			}
			catch(Exception e)
			{
				s = null;
			}

			if (s != null)
			{
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
			if(isNullOrBlank(s.getFname())|| isNullOrBlank(s.getFname()) || isNullOrBlank(s.getAddr()) || isNullOrBlank(s.getCity()) ||
					isNullOrBlank(s.getState()) || isNullOrBlank(s.getZip()) || isNullOrBlank(s.getTele()) || isNullOrBlank(s.getEmail())) {
				return Response.status(Status.BAD_REQUEST).entity("Required field missing").build();
			}
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			String surveyString = "";
			
			try
			{
				surveyString = mapper.writeValueAsString(s);
			}
			catch (Exception e)
			{
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity(e.getMessage()).build();
			}
			
			String topicName = "survey-data-topic";
			
			Properties producerProps = new Properties();
			
			producerProps.put("bootstrap.servers", "184.72.100.51:32710");  //"localhost:9092");
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
