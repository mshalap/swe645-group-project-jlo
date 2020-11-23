package swe645.entities;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.*;
//import javax.xml.bind.annotation.XmlRootElement;

//@Entity
//@XmlRootElement
public class Survey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;
	
	private String fname;
	private String lname;
	private String addr;
	private String city;
	private String state;
	public int getId() {
		return id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStudents() {
		return students;
	}
	public void setStudents(String students) {
		this.students = students;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getDorm() {
		return dorm;
	}
	public void setDorm(String dorm) {
		this.dorm = dorm;
	}
	public String getAtmosphere() {
		return atmosphere;
	}
	public void setAtmosphere(String atmosphere) {
		this.atmosphere = atmosphere;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getRaffle() {
		return raffle;
	}
	public void setRaffle(String raffle) {
		this.raffle = raffle;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	private String zip;
	private String tele;
	private String email;
	private Date date;
	private String students;
	private String location;
	private String campus;
	private String dorm;
	private String atmosphere;
	private String sports;
	private String interest;
	private String recommendation;
	private String raffle;
	
	//@Column(length = 2000)
	private String comments;
	
	
	
	
}
