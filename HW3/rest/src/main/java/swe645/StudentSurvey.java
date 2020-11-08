package swe645;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import swe645.entities.*;

@Stateless
@Path("survey")
public class StudentSurvey{

	public static boolean isNullOrBlank(String param) { 
	    return param == null || param.trim().length() == 0;
	}
	
	
	@PersistenceContext(name = "HW3")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("HW3");

	
	
	/*
	 * Test code
	@GET
	//public List<Survey> get() {
	public String get() {
		
		return "Hello World";
	}
	
	*/
	
	@GET
	@Produces("application/json")
	public List<Survey> get() {
		EntityManager em = emf.createEntityManager();
		CriteriaQuery<Survey> cq = em.getCriteriaBuilder().createQuery(Survey.class);
		cq.select(cq.from(Survey.class));
		return em.createQuery(cq).getResultList();
	}
	
	@POST
	@Consumes("application/json")
	public Response postdata(Survey s){
		try {
			if(isNullOrBlank(s.getFname())|| isNullOrBlank(s.getFname()) || isNullOrBlank(s.getAddr()) || isNullOrBlank(s.getCity()) ||
					isNullOrBlank(s.getState()) || isNullOrBlank(s.getZip()) || isNullOrBlank(s.getTele()) || isNullOrBlank(s.getEmail())) {
				return Response.status(Status.BAD_REQUEST).entity("Required field missing").build();
			}
			
				
			//create entity and persist.
			/*Survey s = new Survey();
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
			s.setComments(comments);*/
			
			/*Survey s = new Survey();
			s.setFname("J");
			s.setLname("Lopez");
			s.setAddr("youwish");
			s.setCity("hoville");
			s.setState("va");
			s.setZip("22044");
			s.setTele("8675309");
			s.setEmail("jlo@theblock.org");
			s.setDate(null);
			s.setStudents(null);
			s.setLocation(null);
			s.setCampus(null);
			s.setDorm(null);
			s.setAtmosphere(null);
			s.setSports(null);
			s.setInterest(null);
			s.setRecommendation(null);
			s.setRaffle(null);
			s.setComments(null);*/
			
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			
			try {
				et.begin();
				em.persist(s);
				et.commit();
		
			} catch (Exception e) {
				try {
					et.rollback();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity(e.getMessage()).build();
			}
		
		}catch(Exception e) {
			return Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build();
		}
		return Response.created(null).build();
	}
}
