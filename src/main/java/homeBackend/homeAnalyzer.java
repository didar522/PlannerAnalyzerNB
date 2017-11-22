package homeBackend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import dataPreprocess.Preprocessing;
import dataTemplates.DataIssueTemplate;
import dataTemplates.resultTemplate;
import optimizer.NSGAIIMultiRunner;
import optimizer.problemDefinition;
import planning.relPlanning;
import planning.relRePlanning;


public class homeAnalyzer {

	Date lastPLanning = null, newReplanning = null, releaseStart=null, releaseEnd = null;  
	
	ArrayList<DataIssueTemplate> allIssueData = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> proposedIssueData = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> backlogIssueData = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> offeredIssueData = new ArrayList<DataIssueTemplate>();  
	ArrayList<Date> list_replanning=new ArrayList <Date>(); 
	
	Preprocessing obj_Filtering; 
	relPlanning obj_relPlanning ; 
	relRePlanning obj_relRePlanning;
	resultTemplate obj_resultFormat = new resultTemplate(); 
	
	int daysForReplan = 0; 
	double dblAvailableCapacity=0;
	int bugRatio=0, ftrRatio=0, impRatio=0; 
	
	public homeAnalyzer (Date tmpreleaseStart, Date tmpreleaseEnd, int tmpdaysForReplan, ArrayList<DataIssueTemplate> tmp_allIssueData){
		this.releaseStart=tmpreleaseStart;
		this.releaseEnd=tmpreleaseEnd;
		this.daysForReplan = tmpdaysForReplan;
		allIssueData = tmp_allIssueData; 
	}
	
	public void dataPreprocess (){
		obj_Filtering=new Preprocessing(allIssueData, releaseStart, releaseEnd);
		backlogIssueData= obj_Filtering.filterIssuesEarlyOpen();
		dblAvailableCapacity = obj_Filtering.filterIssueInClose();;
		
		bugRatio=(int) (obj_Filtering.bugTimeSpent*100/(obj_Filtering.ftrTimeSpent+obj_Filtering.impTimeSpent+obj_Filtering.bugTimeSpent));
	    ftrRatio=(int) (obj_Filtering.ftrTimeSpent*100/(obj_Filtering.ftrTimeSpent+obj_Filtering.impTimeSpent+obj_Filtering.bugTimeSpent));
	    impRatio=(int)(obj_Filtering.impTimeSpent*100/(obj_Filtering.ftrTimeSpent+obj_Filtering.impTimeSpent+obj_Filtering.bugTimeSpent));
	    
	}
	
	public resultTemplate runPlanner () throws Exception{
		list_replanning.add(releaseStart); 
		
		dataPreprocess (); 
		obj_relPlanning = new relPlanning(); 
		obj_relPlanning.performRelPlanning(backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 
		proposedIssueData=obj_relPlanning.identifyRandOffered();
	
	
		lastPLanning = list_replanning.get(list_replanning.size()-1); 
		newReplanning = new Date(list_replanning.get(list_replanning.size()-1).getTime()+daysForReplan*24*60*60*1000); 
		
		
		while (newReplanning.before(releaseEnd)){
			
			list_replanning.add(newReplanning); 
			
			backlogIssueData=null; 
			obj_relPlanning = null; 
			
			obj_relRePlanning = new relRePlanning (allIssueData, proposedIssueData, lastPLanning, newReplanning, bugRatio, ftrRatio, impRatio);
			backlogIssueData=obj_relRePlanning.preprocessRePlanning ();
			
			double dblUsedCapacity =0; 
			for (int i=0;i<backlogIssueData.size();i++){
				if (backlogIssueData.get(i).isOffered()==true){
					offeredIssueData.add(backlogIssueData.get(i)); 
					
					dblUsedCapacity += backlogIssueData.get(i).getDefaultTimespent();
					backlogIssueData.remove(i); 
				}
			}
			
			dblAvailableCapacity = dblAvailableCapacity-dblUsedCapacity; 
			
			 
			
			obj_relPlanning = new relPlanning(); 
			obj_relPlanning.performRelPlanning(backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 
			proposedIssueData=obj_relPlanning.identifyRandOffered();
		
			lastPLanning = list_replanning.get(list_replanning.size()-1);
			newReplanning = new Date(list_replanning.get(list_replanning.size()-1).getTime()+daysForReplan*24*60*60*1000);
		}
		
		return calculateResults (); 
	
	}
	
	
public resultTemplate calculateResults (){
		
		int totalValue = 0, totalActualValue=0; 
		double totalFtrTimeSpent = 0, totalBugTimeSpent = 0,totalImpTimeSpent = 0; 
		
		for (DataIssueTemplate iterator: offeredIssueData){
			
                    if (iterator.isOffered()==true){
                        totalValue += iterator.getPriorityValue(); 
			
			if (iterator.getIssueTypeValue()==1){
				totalFtrTimeSpent += iterator.getDefaultTimespent(); 
			}
			
			else if (iterator.getIssueTypeValue()==2){
				totalBugTimeSpent += iterator.getDefaultTimespent(); 
			}
			else if(iterator.getIssueTypeValue()==1){
				totalImpTimeSpent += iterator.getDefaultTimespent(); 
			}
                    }
		}
		
		for (DataIssueTemplate iterator: obj_Filtering.listInClose){
			totalActualValue += iterator.getPriorityValue();
		}
		
		obj_resultFormat.daysForReplan=daysForReplan; 
	    obj_resultFormat.totalActualValue =  totalActualValue; 
	    obj_resultFormat.totalValue = totalValue; 
	    obj_resultFormat.actftrRatio=ftrRatio;
	    obj_resultFormat.actbugRatio = bugRatio;
	    obj_resultFormat.actimpRatio=impRatio; 
	    obj_resultFormat.totalFtrTimeSpent=totalFtrTimeSpent;
	    obj_resultFormat.totalBugTimeSpent=totalBugTimeSpent;
	    obj_resultFormat.totalImpTimeSpent=totalImpTimeSpent; 
	    obj_resultFormat.prpftrRatio=(totalFtrTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
	    obj_resultFormat.prpbugRatio=(totalBugTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
	    obj_resultFormat.prpimpRatio=(totalImpTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
	    obj_resultFormat.distance = Math.sqrt(Math.pow((obj_resultFormat.actftrRatio-obj_resultFormat.prpftrRatio),2)+Math.pow((obj_resultFormat.actbugRatio-obj_resultFormat.prpbugRatio),2)+Math.pow((obj_resultFormat.actimpRatio-obj_resultFormat.prpimpRatio),2));
	
	    return  obj_resultFormat; 

	}
}//end class




