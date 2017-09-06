package dataTemplates;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dataTemplates.ReleaseCalendarTemplate; 

public class ReleaseInfoCollection {
	ArrayList<ReleaseCalendarTemplate> releaseInfo = new ArrayList<ReleaseCalendarTemplate> ();
	   
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	public ReleaseInfoCollection (ArrayList<ReleaseCalendarTemplate> tmp_releaseInfo){
		this.releaseInfo = tmp_releaseInfo; 
		
//		releaseInfo.add(sdf.parse("2011-11-23"));
//		releaseInfo.add(sdf.parse("2015-04-13"));
	}
	
	public void fillUpReleaseInfo (String tmp_strInputdate, boolean boolIsFirstRelease, String tmp_strCategoryofRelease) {
		ReleaseCalendarTemplate tmp_objReleaseCalendarTemplate = new ReleaseCalendarTemplate (); 
		Date tmpInputDate= null; 
		
		try{
			tmpInputDate = sdf.parse(tmp_strInputdate); 
		} 
		catch (Exception e){ 
			System.out.println("Problem in parsing release dates");
		}
		
		
		
		tmp_objReleaseCalendarTemplate.setDateReleasEnd(tmpInputDate);
		
		if (boolIsFirstRelease == true){
			tmp_objReleaseCalendarTemplate.setStrCategoryofRelease(tmp_strCategoryofRelease);
			tmp_objReleaseCalendarTemplate.setDblTotalDurationofRelease(0);
		}
		
		else {
			tmp_objReleaseCalendarTemplate.setStrCategoryofRelease(tmp_strCategoryofRelease);
			Date tmp_dateLastReleaseEnd = releaseInfo.get(releaseInfo.size()-1).getDate();
			tmp_objReleaseCalendarTemplate.setDblTotalDurationofRelease(getDateDiff(tmp_dateLastReleaseEnd,tmpInputDate,TimeUnit.DAYS));
//			System.out.println(tmp_objReleaseCalendarTemplate.getDblTotalDurationofRelease());
		}
		
		releaseInfo.add(tmp_objReleaseCalendarTemplate);
	}
	
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public Date getReleaseDate (int index){
		return releaseInfo.get(index).getDate(); 
	}
}
