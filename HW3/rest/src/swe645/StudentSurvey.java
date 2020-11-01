package swe645;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;

import swe645.entities.*;

@Path("survey")
public class StudentSurvey {

	/*
	 * Test code*/
	@GET
	//public List<Survey> get() {
	public String get() {
		
		return "Hello World";
	}
	
	
	
	/*@GET
	public List<Survey> get() {
		List<Survey> retval = new ArrayList<Survey>();
		return retval;
	}
	
	@POST
	@Consumes("applicatoin/x-www-form-urlencoded")
	public void postdata(@FormParam("blah") int blah
						
						) {
		
	}*/
}
