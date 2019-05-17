package entites;

public abstract class LatencyReport {
	
	protected int RegularBookLatePeriodAvg;
	protected int RegularBookLatePeriodMedian;
	protected int RegularBookLatePeriodDecimaldistribution;
	
	public LatencyReport(int regularBookLatePeriodAvg, int regularBookLatePeriodMedian,
			int regularBookLatePeriodDecimaldistribution) {
		super();
		RegularBookLatePeriodAvg = regularBookLatePeriodAvg;
		RegularBookLatePeriodMedian = regularBookLatePeriodMedian;
		RegularBookLatePeriodDecimaldistribution = regularBookLatePeriodDecimaldistribution;
	}
	
	public int getRegularBookLatePeriodAvg() {
		return RegularBookLatePeriodAvg;
	}
	public void setRegularBookLatePeriodAvg(int regularBookLatePeriodAvg) {
		RegularBookLatePeriodAvg = regularBookLatePeriodAvg;
	}
	public int getRegularBookLatePeriodMedian() {
		return RegularBookLatePeriodMedian;
	}
	public void setRegularBookLatePeriodMedian(int regularBookLatePeriodMedian) {
		RegularBookLatePeriodMedian = regularBookLatePeriodMedian;
	}
	public int getRegularBookLatePeriodDecimaldistribution() {
		return RegularBookLatePeriodDecimaldistribution;
	}
	public void setRegularBookLatePeriodDecimaldistribution(int regularBookLatePeriodDecimaldistribution) {
		RegularBookLatePeriodDecimaldistribution = regularBookLatePeriodDecimaldistribution;
	}
}
