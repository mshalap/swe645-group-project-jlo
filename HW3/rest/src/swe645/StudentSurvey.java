package swe645;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import swe645.entities.*;

@Path("survey")
public class StudentSurvey {

	/*
	 * Test code
	@GET
	//public List<Survey> get() {
	public String get() {
		
		return "Hello World";
	}
	
	*/
	
	@GET
	public List<Survey> get() {
		List<Survey> retval = new ArrayList<Survey>();
		return retval;
	}
	
	@POST
	@Consumes("applicatoin/x-www-form-urlencoded")
	public Response postdata(
			@FormParam("First Name") String fname,
			@FormParam("Last Name") String lname,
			@FormParam("Street Address") String addr,
			@FormParam("City") String city,
			@FormParam("State") String state,
			@FormParam("Zip Code") String zip,
			@FormParam("Telephone Number") String tele,
			@FormParam("Email Address") String email,
			@FormParam("Survey Date") Date date,
			@FormParam("students") String students,
			@FormParam("location") String location,
			@FormParam("campus") String campus,
			@FormParam("dorm") String dorm,
			@FormParam("atmosphere") String atmosphere,
			@FormParam("sports") String sports,
			@FormParam("interest") String interest,
			@FormParam("option") String recommendation,
			@FormParam("Raffle") String raffle,
			@FormParam("Additional Comments") String comments
			){
		
		if(fname.isEmpty() || lname.isEmpty() || addr.isEmpty() || city.isEmpty() ||
				state.isEmpty() || zip.isEmpty() || tele.isEmpty() || email.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Required field missing").build();
		}
			
		
		
		return Response.created(null).build();
	}
}
