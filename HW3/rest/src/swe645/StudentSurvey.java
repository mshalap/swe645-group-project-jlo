package swe645;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import javax.ejb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import swe645.entities.*;

@Stateless
@Path("survey")
public class StudentSurvey{

	@PersistenceContext(name = "HW3")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("HW3");

	@Resource
	private UserTransaction utx;
	
	
	/*
	 * Test code
	@GET
	//public List<Survey> get() {
	public String get() {
		
		return "Hello World";
	}
	
	*/
	
	@GET
	@Produces("application/xml")
	public List<Survey> get() {
		EntityManager em = emf.createEntityManager();
		CriteriaQuery<Survey> cq = em.getCriteriaBuilder().createQuery(Survey.class);
		cq.select(cq.from(Survey.class));
		return em.createQuery(cq).getResultList();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
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
		
			
		//create entity and persist.
		Survey s = new Survey();
		s.setFname(fname);
		s.setLname(lname);
		s.setAddr(addr);
		s.setCity(city);
		s.setState(state);
		s.setZip(zip);
		s.setTele(tele);
		s.setEmail(email);
		s.setDate(date);
		s.setStudents(students);
		s.setLocation(location);
		s.setCampus(campus);
		s.setDorm(dorm);
		s.setAtmosphere(atmosphere);
		s.setSports(sports);
		s.setInterest(interest);
		s.setRecommendation(recommendation);
		s.setRaffle(raffle);
		s.setComments(comments);
		
		EntityManager em = emf.createEntityManager();
		try {
			utx.begin();
			em.persist(s);
			utx.commit();
			
		} catch (Exception e) {
			try {
				utx.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		
		return Response.created(null).build();
	}
}
