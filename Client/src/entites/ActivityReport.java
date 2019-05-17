package entites;

public class ActivityReport {
	protected int NumberOfActiveUsers;
	protected int NumberOfFreezeUsers;
	protected int NumberOfLockedUsers;
	protected int NumberOfLoanedCopies;
	protected int NumberOfAccountsHaveLate;
	
	public ActivityReport(int numberOfActiveUsers, int numberOfFreezeUsers, int numberOfLockedUsers,
			int numberOfLoanedCopies, int numberOfAccountsHaveLate) {
		super();
		NumberOfActiveUsers = numberOfActiveUsers;
		NumberOfFreezeUsers = numberOfFreezeUsers;
		NumberOfLockedUsers = numberOfLockedUsers;
		NumberOfLoanedCopies = numberOfLoanedCopies;
		NumberOfAccountsHaveLate = numberOfAccountsHaveLate;
	}
	
	public int getNumberOfActiveUsers() {
		return NumberOfActiveUsers;
	}
	public void setNumberOfActiveUsers(int numberOfActiveUsers) {
		NumberOfActiveUsers = numberOfActiveUsers;
	}
	public int getNumberOfFreezeUsers() {
		return NumberOfFreezeUsers;
	}
	public void setNumberOfFreezeUsers(int numberOfFreezeUsers) {
		NumberOfFreezeUsers = numberOfFreezeUsers;
	}
	public int getNumberOfLockedUsers() {
		return NumberOfLockedUsers;
	}
	public void setNumberOfLockedUsers(int numberOfLockedUsers) {
		NumberOfLockedUsers = numberOfLockedUsers;
	}
	public int getNumberOfLoanedCopies() {
		return NumberOfLoanedCopies;
	}
	public void setNumberOfLoanedCopies(int numberOfLoanedCopies) {
		NumberOfLoanedCopies = numberOfLoanedCopies;
	}
	public int getNumberOfAccountsHaveLate() {
		return NumberOfAccountsHaveLate;
	}
	public void setNumberOfAccountsHaveLate(int numberOfAccountsHaveLate) {
		NumberOfAccountsHaveLate = numberOfAccountsHaveLate;
	}
	

}
