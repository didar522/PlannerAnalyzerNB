package dataTemplates;

import java.util.Date;

public class ReleaseCalendarTemplate {
	private Date dateRelease; 
	private String strCategoryofRelease; //High, Medium, Low (Satisfaction of a release)
	private double dblTotalDurationofRelease; 
	private String releaseName; 
	
	
	
	
	/**
	 * @return the dateWeekStartDate
	 */
	public Date getDate() {
		return dateRelease;
	}
	/**
	 * @param dateWeekStartDate the dateWeekStartDate to set
	 */
	public void setDateReleasEnd(Date tmp_dateReleasEnd) {
		this.dateRelease = tmp_dateReleasEnd;
	}
	
	/**
	 * @return the strCategoryofRelease
	 */
	public String getStrCategoryofRelease() {
		return strCategoryofRelease;
	}
	/**
	 * @param strCategoryofRelease the strCategoryofRelease to set
	 */
	public void setStrCategoryofRelease(String strCategoryofRelease) {
		this.strCategoryofRelease = strCategoryofRelease;
	}
	/**
	 * @return the dblTotalDurationofRelease
	 */
	public double getDblTotalDurationofRelease() {
		return dblTotalDurationofRelease;
	}
	/**
	 * @param dblTotalDurationofRelease the dblTotalDurationofRelease to set
	 */
	public void setDblTotalDurationofRelease(double dblTotalDurationofRelease) {
		this.dblTotalDurationofRelease = dblTotalDurationofRelease;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
}
