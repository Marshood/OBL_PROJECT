package entites;

import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String UserID;
	public String UserPass;
	public String UserType;
	public String IsLogin;
	public String FisrtName;
	public String LastName;
	public String MemberShipStatus;
	public String email;
	public String phone;
	public String GradationDate;
	public String Organization; 
	public String counterLate;
	public String  cardNum;
    public String getEndFreezingDate;
	public String getGetEndFreezingDate() {
		return getEndFreezingDate;
	}

	public void setGetEndFreezingDate(String getEndFreezingDate) {
		this.getEndFreezingDate = getEndFreezingDate;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getFisrtName() {
		return FisrtName;
	}

	public void setFisrtName(String fisrtName) {
		FisrtName = fisrtName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getMemberShipStatus() {
		return MemberShipStatus;
	}

	public void setMemberShipStatus(String memberShipStatus) {
		MemberShipStatus = memberShipStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGradationDate() {
		return GradationDate;
	}

	public void setGradationDate(String gradationDate) {
		GradationDate = gradationDate;
	}

	public String getOrganization() {
		return Organization;
	}

	public void setOrganization(String organization) {
		Organization = organization;
	}

	public String getCounterLate() {
		return counterLate;
	}

	public void setCounterLate(String counterLate) {
		this.counterLate = counterLate;
	}

	public User() {
	}
	
	public User(String UserID, String UserPass, String UserType, String IsLogin) {
		super();
		this.UserID = UserID;
		this.UserPass = UserPass;
		this.UserType = UserType;
		this.IsLogin = IsLogin;
	}


	public String getUserID() {
		return UserID;
	}


	public void setUserID(String userID) {
		UserID = userID;
	}


	public String getUserPass() {
		return UserPass;
	}


	public void setUserPass(String userPass) {
		UserPass = userPass;
	}


	public String getUserType() {
		return UserType;
	}


	public void setUserType(String userType) {
		UserType = userType;
	}


	public String getIsLogin() {
		return IsLogin;
	}


	public void setIsLogin(String isLogin) {
		IsLogin = isLogin;
	}
}
