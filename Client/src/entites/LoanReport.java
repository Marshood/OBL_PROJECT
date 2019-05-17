package entites;

public abstract class LoanReport {
	protected int RegularBookLoanPeriodAvg;
	protected int RegularBookLoanPeriodMedian;
	protected int RegularBookLoanPeriodDecimaldistribution;
	protected int RequestedBookLoanPeriodAvg;
	protected int RequestedBookLoanPeriodMedian;
	protected int RequestedBookLoanPeriodDecimaldistribution;
	
	
	
	
	public LoanReport(int regularBookLoanPeriodAvg, int regularBookLoanPeriodMedian,
			int regularBookLoanPeriodDecimaldistribution, int requestedBookLoanPeriodAvg,
			int requestedBookLoanPeriodMedian, int requestedBookLoanPeriodDecimaldistribution) {
		super();
		this.RegularBookLoanPeriodAvg = regularBookLoanPeriodAvg;
		this.RegularBookLoanPeriodMedian = regularBookLoanPeriodMedian;
		this.RegularBookLoanPeriodDecimaldistribution = regularBookLoanPeriodDecimaldistribution;
		this.RequestedBookLoanPeriodAvg = requestedBookLoanPeriodAvg;
		this.RequestedBookLoanPeriodMedian = requestedBookLoanPeriodMedian;
		this.RequestedBookLoanPeriodDecimaldistribution = requestedBookLoanPeriodDecimaldistribution;
	}
	public int getRegularBookLoanPeriodAvg() {
		return RegularBookLoanPeriodAvg;
	}
	public void setRegularBookLoanPeriodAvg(int regularBookLoanPeriodAvg) {
		RegularBookLoanPeriodAvg = regularBookLoanPeriodAvg;
	}
	public int getRegularBookLoanPeriodMedian() {
		return RegularBookLoanPeriodMedian;
	}
	public void setRegularBookLoanPeriodMedian(int regularBookLoanPeriodMedian) {
		RegularBookLoanPeriodMedian = regularBookLoanPeriodMedian;
	}
	public int getRegularBookLoanPeriodDecimaldistribution() {
		return RegularBookLoanPeriodDecimaldistribution;
	}
	public void setRegularBookLoanPeriodDecimaldistribution(int regularBookLoanPeriodDecimaldistribution) {
		RegularBookLoanPeriodDecimaldistribution = regularBookLoanPeriodDecimaldistribution;
	}
	public int getRequestedBookLoanPeriodAvg() {
		return RequestedBookLoanPeriodAvg;
	}
	public void setRequestedBookLoanPeriodAvg(int requestedBookLoanPeriodAvg) {
		RequestedBookLoanPeriodAvg = requestedBookLoanPeriodAvg;
	}
	public int getRequestedBookLoanPeriodMedian() {
		return RequestedBookLoanPeriodMedian;
	}
	public void setRequestedBookLoanPeriodMedian(int requestedBookLoanPeriodMedian) {
		RequestedBookLoanPeriodMedian = requestedBookLoanPeriodMedian;
	}
	public int getRequestedBookLoanPeriodDecimaldistribution() {
		return RequestedBookLoanPeriodDecimaldistribution;
	}
	public void setRequestedBookLoanPeriodDecimaldistribution(int requestedBookLoanPeriodDecimaldistribution) {
		RequestedBookLoanPeriodDecimaldistribution = requestedBookLoanPeriodDecimaldistribution;
	}

}
