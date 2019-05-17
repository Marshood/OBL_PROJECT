package client;

//package (serverMarshood);
import java.io.*;
public class Student implements Serializable  {

	private String studID;
	private String studentName;
	private String StatusMembership;
	private String Operation;
	private Boolean  Freeze ;
	
	public Student(String studID,String studentName ,String StatusMembership ,String Operation,Boolean Freeze ) {
		this.studID=studID;
		this.studentName=studentName;
		this.StatusMembership=StatusMembership;
		this.Operation=Operation;
		this.Freeze=Freeze;
	}

	public String getStudID() {
		return studID;
	}

	public void setStudID(String studID) {
		this.studID = studID;
		System.out.println(this.studID);
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStatusMembership() {
		return StatusMembership;
	}

	public void setStatusMembership(String statusMembership) {
		StatusMembership = statusMembership;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}

	public Boolean getFreeze() {
		return Freeze;
	}

	public void setFreeze(Boolean freeze) {
		Freeze = freeze;
	}
	public String toString(){
		return String.format("\nID: %s  %s  %s %s %s",studID,studentName,StatusMembership,Operation,Freeze);
	}

}
