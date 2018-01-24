package homeBackend;

import java.util.ArrayList;
import java.util.Date;

import dataPreprocess.Preprocessing;
import dataTemplates.DataIssueTemplate;
import dataTemplates.resultTemplate;
import guiImport.importJDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	ArrayList<resultTemplate> list_resultFormat = new ArrayList <resultTemplate>(); 
	Connection connection = null;
        
        
        Preprocessing obj_Filtering; 
	relPlanning obj_relPlanning ; 
	relRePlanning obj_relRePlanning;
	
	
	int daysForReplan = 0; 
	double dblAvailableCapacity=0;
	int bugRatio=0, ftrRatio=0, impRatio=0; 
        int totalThemeCR = 0; 
	
	public homePlanner (Date tmpreleaseStart, Date tmpreleaseEnd, int tmpdaysForReplan, ArrayList<DataIssueTemplate> tmp_allIssueData, double tmp_dblAvailableCapacity, int tmp_ftrRatio, int tmp_bugRatio, int tmp_impRatio){
		this.releaseStart=tmpreleaseStart;
		this.releaseEnd=tmpreleaseEnd;
		this.daysForReplan = tmpdaysForReplan;
		allIssueData = tmp_allIssueData; 
		
		dblAvailableCapacity= tmp_dblAvailableCapacity; 
		
		bugRatio = tmp_bugRatio;
		ftrRatio=tmp_ftrRatio;
		impRatio=tmp_impRatio; 
	}
	
	public void dataPreprocess (){
		obj_Filtering=new Preprocessing(allIssueData, releaseStart, releaseEnd);
		backlogIssueData= obj_Filtering.filterIssuesEarlyOpen();
                
                for (int iterator=0;iterator<backlogIssueData.size();iterator++){
                    System.out.println ("BLID - "+backlogIssueData.get(iterator).getStrKey());
//                    backlogIssueData.get(iterator).setIntThemeValue((int)Math.random()*((9-4)+4));
                    if (backlogIssueData.get(iterator).getIntThemeValue ()>=7){
                        totalThemeCR++; 
                    }
                }
	}
	
	public ArrayList<resultTemplate> runPlanner (boolean isplanning) throws Exception{
		dataPreprocess (); 
		obj_relPlanning = new relPlanning(); 
		obj_relPlanning.performRelPlanning(backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 
		
		System.out.println("Size of the solution +++++"+ obj_relPlanning.transfernonDominatedSolutions.size());
		
                System.out.println("solutionNum,totalThemeValue,themeCR,themeCRoffered,themeCoverage,totalValue,totalFtrValue,totalBugValue,totalImpValue,totalExtraValue,totalCapacity,totalCost,totalFtrCost,totalBugCost,totalImpCost,totalExtraCost,totalIssuesOffered,totalFtrOffered,totalBugOffered,totalImpOffered");
                getResultsDisplay (obj_relPlanning.transfernonDominatedSolutions.size()); 
//		getResultsIntoDB (isplanning);
                    
              		

		
                
		
//		calculateResults (proposedIssueData3,3);
                
                return list_resultFormat; 
	}
	
	
        public void getResultsDisplay (int paretoFrontSize){
            for (int i=0;i<paretoFrontSize;i++){
                calculateResults (obj_relPlanning.identifyOfferedforChoice(i),i);
            }
        }
        
        public void getResultsIntoDB (boolean isplanning){
            // For getting the data in DB and display on the output window and also required for replanning actions. For analysis of the results we keep it off now. 
                try{
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:DB/BSQPLanner.DB.sqlite");//
                   
                    proposedIssueData1=obj_relPlanning.identifyOfferedforChoice(0); 
                    String strQuery0 = "Insert into OfferedIssueData values (?,?,?,?,?,?);";
                    storePlanInDB (proposedIssueData1,1,strQuery0); 
                    calculateResults (proposedIssueData1,1);
                    
                    if (isplanning=true){
                        String BUPstrQuery0 = "Insert into BUPOfferedIssueData values (?,?,?,?,?,?);";
                        storePlanInDB (proposedIssueData1,1,BUPstrQuery0); 
                    }
                    
                    if (obj_relPlanning.transfernonDominatedSolutions.size()>1){
                        proposedIssueData2=obj_relPlanning.identifyOfferedforChoice(1); 
                        String strQuery1 = "Insert into OfferedIssueData1 values (?,?,?,?,?,?);";
                        storePlanInDB (proposedIssueData2,2,strQuery1);
                        calculateResults (proposedIssueData2,2);
                    }
//                    storePlanInDB (proposedIssueData3,3);

                    connection.close();
                }
                catch (Exception ex){
                    System.out.println("homeBackend.homePlanner.runPlanner()");
                    Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            // For getting the data in DB and display on the output window and also required for replanning actions. For analysis of the results we keep it off now.
        }
	
        public void storePlanInDB (ArrayList<DataIssueTemplate> planIssueData, int solutionNum, String tmpStrQuery){
            try {
//                String strQuery = "Insert into OfferedIssueData values (?,?,?,?,?,?);";
                String strQuery=tmpStrQuery;
                PreparedStatement preparedStatement = connection.prepareStatement(strQuery); 

                for (int i=0;i<planIssueData.size();i++){
//                for (int i=0;i<allIssueData.size();i++){
                    
                    preparedStatement.setString(1, planIssueData.get(i).getStrKey());
                    preparedStatement.setString(2, planIssueData.get(i).getStrSummary());
                    preparedStatement.setString(3, planIssueData.get(i).getStrStatus());
                    preparedStatement.setString(4, planIssueData.get(i).getStrPriority());
                    preparedStatement.setString(5, planIssueData.get(i).getStrResolution());
                    
                    if (planIssueData.get(i).isOffered()==true) {
                        preparedStatement.setString(6, "offered");
                    }
                    else {
                        preparedStatement.setString(6, "");
                    }
                    preparedStatement.executeUpdate();
                }
            }
            catch (Exception ex) {
                System.out.println("homeBackend.homePlanner.storePlanInDB()");
                Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
    }        
	
public void calculateResults (ArrayList<DataIssueTemplate> displayIssueData, int solutionNum){
		
	for (DataIssueTemplate iterator:displayIssueData){
//		System.out.println(iterator.getStrKey() +"--"+iterator.isOffered());
	}	
	
	
	
	int totalValue = 0, totalThemeValue=0, themeOffered = 0, themeCoverage = 0, totalCost=0; 
	double totalFtrTimeSpent = 0, totalBugTimeSpent = 0,totalImpTimeSpent = 0, totalExtraTimeSpent = 0; 
        int totalFtrValue = 0, totalBugValue = 0,totalImpValue = 0, totalExtraValue =0;
        int totalIssue = 0, totalFtr = 0, totalBug=0, totalImp=0; 
                
                for (DataIssueTemplate iterator: displayIssueData){
                    if (iterator.isOffered()==true){
                    
                    totalValue += iterator.getPriorityValue(); 
                    totalCost += iterator.getDefaultTimespent();
                    totalIssue++; 
                            
                    	if (iterator.getIssueTypeValue()==1){
				totalFtrTimeSpent += iterator.getDefaultTimespent(); 
                                totalFtrValue += iterator.getPriorityValue(); 
                                totalFtr++; 
			}
			
			else if (iterator.getIssueTypeValue()==2){
				totalBugTimeSpent += iterator.getDefaultTimespent(); 
                                totalBugValue += iterator.getPriorityValue();
                                totalBug++; 
			}
			else if(iterator.getIssueTypeValue()==3){
				totalImpTimeSpent += iterator.getDefaultTimespent(); 
                                totalImpValue += iterator.getPriorityValue(); 
                                totalImp++; 
			}
                        else {
                            totalExtraTimeSpent += iterator.getDefaultTimespent();
                            totalExtraValue += iterator.getPriorityValue();
                        }
                    }    
                }
                
                for (DataIssueTemplate iterator: displayIssueData){
                    if (iterator.isOffered()==true){
                        totalThemeValue += iterator.getIntThemeValue();
                        
                        if (iterator.getIntThemeValue()>=7){
                            themeOffered++; 
                            
                        }
                    }
                }
                
            themeCoverage=100*themeOffered/totalThemeCR; 
            
            
            resultTemplate obj_resultFormat = new resultTemplate(); 
                
            obj_resultFormat.solutionNumber = solutionNum; 
            obj_resultFormat.daysForReplan=daysForReplan; 
	    obj_resultFormat.totalValue = totalValue; 
            obj_resultFormat.totalThemeValue = totalThemeValue; 
            obj_resultFormat.totalFtrValue = totalFtrValue;
            obj_resultFormat.totalBugValue = totalBugValue;
            obj_resultFormat.totalImpValue = totalImpValue;
            
            obj_resultFormat.themeCoverage = themeCoverage; 
           
//	    obj_resultFormat.actftrRatio=ftrRatio;
//	    obj_resultFormat.actbugRatio = bugRatio;
//	    obj_resultFormat.actimpRatio=impRatio; 
//	    obj_resultFormat.totalFtrTimeSpent=totalFtrTimeSpent;
//	    obj_resultFormat.totalBugTimeSpent=totalBugTimeSpent;
//	    obj_resultFormat.totalImpTimeSpent=totalImpTimeSpent; 
//	    obj_resultFormat.prpftrRatio=(totalFtrTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
//	    obj_resultFormat.prpbugRatio=(totalBugTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
//	    obj_resultFormat.prpimpRatio=(totalImpTimeSpent*100/(totalFtrTimeSpent+totalBugTimeSpent+totalImpTimeSpent));
//	    obj_resultFormat.distance = Math.sqrt(Math.pow((obj_resultFormat.actftrRatio-obj_resultFormat.prpftrRatio),2)+Math.pow((obj_resultFormat.actbugRatio-obj_resultFormat.prpbugRatio),2)+Math.pow((obj_resultFormat.actimpRatio-obj_resultFormat.prpimpRatio),2));
            list_resultFormat.add(obj_resultFormat); 
            
            
	    System.out.println(solutionNum+ ","+totalThemeValue+ ","+totalThemeCR+ ","+themeOffered+ ","+themeCoverage+ "," 
                    +totalValue  +","+totalFtrValue+","+totalBugValue+","+totalImpValue+","+totalExtraValue+","
                    +dblAvailableCapacity+","+totalCost+","+totalFtrTimeSpent+","+totalBugTimeSpent+","+totalImpTimeSpent+","+totalExtraTimeSpent
                    +","+totalIssue+","+totalFtr+","+totalBug+","+totalImp);


	}
}//end class



//Deleted codes bup

//	public resultTemplate runRePlanner2 throws IOException {
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


