package entites;

import java.io.Serializable;

public class LibrarianNew implements Serializable {
	public LibrarianNew() {
		super();
	}

	private static final long serialVersionUID = 1L;
	protected String UserID;
	protected String FirstName;
	protected String LastName;
	protected String Email;
	protected String PhoneNumber;
	protected String Organization;
 
	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getOrganization() {
		return Organization;
	}

	public void setOrganization(String organization) {
		Organization = organization;
	}
}