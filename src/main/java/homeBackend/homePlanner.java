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


public class homePlanner {

	Date lastPLanning = null, newReplanning = null, releaseStart=null, releaseEnd = null;  
	
	ArrayList<DataIssueTemplate> allIssueData = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> proposedIssueData1 = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> proposedIssueData2 = new ArrayList<DataIssueTemplate>();  
	ArrayList<DataIssueTemplate> proposedIssueData3 = new ArrayList<DataIssueTemplate>();  
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
	
	public homePlanner (Date tmpreleaseStart, Date tmpreleaseEnd, int tmpdaysForReplan, ArrayList<DataIssueTemplate> tmp_allIssueData, double tmp_dblAvailableCapacity, int tmp_bugRatio, int tmp_ftrRatio, int tmp_impRatio){
		this.releaseStart=tmpreleaseStart;
		this.releaseEnd=tmpreleaseEnd;
		this.daysForReplan = tmpdaysForReplan;
		allIssueData = tmp_allIssueData; 
		
		dblAvailableCapacity= tmp_dblAvailableCapacity; 
		
		bugRatio = tmp_bugRatio;
		ftrRatio=tmp_bugRatio;
		impRatio=tmp_bugRatio; 
	}
	
	public void dataPreprocess (){
		obj_Filtering=new Preprocessing(allIssueData, releaseStart, releaseEnd);
		backlogIssueData= obj_Filtering.filterIssuesEarlyOpen();
	}
	
	public void runPlanner () throws IOException{
		dataPreprocess (); 
		obj_relPlanning = new relPlanning(); 
		obj_relPlanning.performRelPlanning(backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 
		
		
		proposedIssueData1=obj_relPlanning.identifyOfferedforChoice(0); 
		proposedIssueData1=obj_relPlanning.identifyOfferedforChoice(obj_relPlanning.transfernonDominatedSolutions.size()-1); 
		proposedIssueData1=obj_relPlanning.identifyOfferedforChoice((int)(obj_relPlanning.transfernonDominatedSolutions.size()-1)/2); 
		
		calculateResults (proposedIssueData1);
		calculateResults (proposedIssueData2);
		calculateResults (proposedIssueData3);
	}
	
	
//	public resultTemplate runRePlanner throws IOException {
//		lastPLanning = list_replanning.get(list_replanning.size()-1); 
//		newReplanning = new Date(list_replanning.get(list_replanning.size()-1).getTime()+daysForReplan*24*60*60*1000); 
//		
//		
//		while (newReplanning.before(releaseEnd)){
//			
//			list_replanning.add(newReplanning); 
//			
//			backlogIssueData=null; 
//			obj_relPlanning = null; 
//			
//			obj_relRePlanning = new relRePlanning (allIssueData, proposedIssueData, lastPLanning, newReplanning, bugRatio, ftrRatio, impRatio);
//			backlogIssueData=obj_relRePlanning.preprocessRePlanning ();
//			
//			double dblUsedCapacity =0; 
//			for (int i=0;i<backlogIssueData.size();i++){
//				if (backlogIssueData.get(i).isOffered()==true){
//					offeredIssueData.add(backlogIssueData.get(i)); 
//					
//					dblUsedCapacity += backlogIssueData.get(i).getDefaultTimespent();
//					backlogIssueData.remove(i); 
//				}
//			}
//			
//			dblAvailableCapacity = dblAvailableCapacity-dblUsedCapacity; 
//			
//			 
//			
//			obj_relPlanning = new relPlanning(); 
//			proposedIssueData=obj_relPlanning.performRelPlanning(backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 
//		
//			
//			newReplanning = new Date(list_replanning.get(list_replanning.size()-1).getTime()+daysForReplan*24*60*60*1000);
//		}
//		
//		return calculateResults (); 
	
//	}
	
	
public void calculateResults (ArrayList<DataIssueTemplate> displayIssueData){
		
	for (DataIssueTemplate iterator:displayIssueData){
		System.out.println(iterator.getStrKey() +"--"+iterator.isOffered());
	}	
	
	
	
	int totalValue = 0; 
	double totalFtrTimeSpent = 0, totalBugTimeSpent = 0,totalImpTimeSpent = 0; 
		
		for (DataIssueTemplate iterator: displayIssueData){
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
		
		obj_resultFormat.daysForReplan=daysForReplan; 
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
		
	    System.out.println(obj_resultFormat.totalValue + "--"+ obj_resultFormat.distance+"--"+obj_resultFormat.prpftrRatio+"--"+obj_resultFormat.prpbugRatio+"--"+obj_resultFormat.prpimpRatio);


	}
}//end class




