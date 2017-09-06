package dataTemplates;

public class OptVarTemplate {

	private int value; 
	private double cost;
	private int issueType; //1=ftr,2=bug,3=imp
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getissueType() {
		return issueType;
	}
	public void setissueType(int type) {
		this.issueType = type;
	} 
}
