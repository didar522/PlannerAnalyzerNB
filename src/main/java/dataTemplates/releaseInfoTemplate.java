package dataTemplates;

import java.sql.Date;

public class releaseInfoTemplate {
	public int releaseNum;
	public String releaseName; 
	public int bugs_reported;
	public int ftr_reported;
	public int bugs_resolved;
	public int ftr_resolved;
	public int bugsSevenDays; 
	public int ftrSevenDays;
	public double releaseDuration; 
	public int commitsPerformed; 
	public int addLoc; 
	public int delLoc; 
	public int totalLoc; 
	public Date dateReleasEnd; 
	public String strQualityCategoryofRelease; //High, Medium, Low (Satisfaction of a release)
	public double dblTotalDurationofRelease;
	
	public releaseInfoTemplate (int releaseNum, String releaseName, Date dateReleasEnd, double releaseDuration){
		this.releaseNum= releaseNum;
		this.releaseName=releaseName;
		this.dateReleasEnd = dateReleasEnd; 
		this.releaseDuration = releaseDuration; 
	}
	
	
}
