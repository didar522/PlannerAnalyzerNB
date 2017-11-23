package planning;

import OptimizerSMPSO.SMPSOMultiRunner;
import OptimizerSMPSO.SMPSOSingleRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.solution.DoubleSolution;

import dataTemplates.DataIssueTemplate;
import optimizer.NSGAIIMultiRunner;
import optimizer.problemDefinition;
import org.uma.jmetal.solution.IntegerSolution;

public class relRePlanning {
	
	public ArrayList<DataIssueTemplate> allIssueData; 
	public ArrayList<DataIssueTemplate> proposedIssueData; 
	public ArrayList<DataIssueTemplate> planningIssues=new ArrayList<DataIssueTemplate> (); 
	
	List<DoubleSolution> transfernonDominatedSolutions;
//        List<IntegerSolution> transfernonDominatedSolutions;
	
	public double spenteffort=0.0; 
	int bugRatio=0;
	int ftrRatio=0;
	int impRatio=0; 
	
	Date lastPlanning = null; 
	Date newReplan = null; 
	
	public relRePlanning (ArrayList<DataIssueTemplate> allIssueData, ArrayList<DataIssueTemplate> tmp_proposedIssueData, Date lastPlanning, Date newReplan,  int bugRatio, int ftrRatio, int impRatio){
		this.proposedIssueData = tmp_proposedIssueData; 
		this.allIssueData = allIssueData; 
		this.lastPlanning = lastPlanning; 
		this.newReplan = newReplan; 
		this.bugRatio = bugRatio; 
	    this.ftrRatio = ftrRatio; 
	    this.impRatio = impRatio;
	}
	
	public ArrayList<DataIssueTemplate> preprocessRePlanning (){
		preProcessIssueCapacity ();
		flashIssueList ();
		simulateDevelopment ();
		modifyingIssueList (); 
		
		return  addNewlyOpenedIssues (); 
	}
	
	public void preProcessIssueCapacity (){
		for (DataIssueTemplate iterator: proposedIssueData){
			if (iterator.isOffered()==true){
				planningIssues.add(iterator); 
			}
		}
		
		for (DataIssueTemplate iterator: allIssueData){
			if (iterator.getDateResolved()!=null){
				if(iterator.getDateResolved().after(lastPlanning) && iterator.getDateResolved().before(newReplan)){
//					Date tmpDiffDate =null; 
//					if (iterator.getDateUpdated().before(lastPlanning)){
//						tmpDiffDate = lastPlanning; 
//					}
//					else {
//						tmpDiffDate=iterator.getDateUpdated();
//					}
					
					spenteffort += iterator.getDefaultTimespent(); 
				}
			}
		}
	}
	
	public void flashIssueList (){
		for (int i=0;i<proposedIssueData.size();i++){
			proposedIssueData.get(i).setOffered(false); 
		}
		
		for (int i=0;i<planningIssues.size();i++){
			planningIssues.get(i).setOffered(false); 
		}
	}
	
	public void simulateDevelopment (){
		System.out.println("@@@@@@@@@@@ simulating dev start for size "+ planningIssues.size()+" spent effort "+ spenteffort + "-"+ bugRatio + "-"+ftrRatio + "-"+impRatio);
            
                problemDefinition obj_problemDefinition = new problemDefinition (planningIssues, spenteffort, bugRatio, ftrRatio, impRatio); 
		
                NSGAIIMultiRunner obj_NSGAIIMultiRunner = new NSGAIIMultiRunner (obj_problemDefinition); 
//                SMPSOSingleRunner obj_SMPSOSingleRunner = new SMPSOSingleRunner (obj_problemDefinition);
		
		try{
			transfernonDominatedSolutions=obj_NSGAIIMultiRunner.NSGARunner();
//                        transfernonDominatedSolutions=obj_SMPSOSingleRunner.SMPSORunner();
		}catch (Exception ex){
		}
                
//                SMPSOMultiRunner obj_SMPSOMultiRunner = new SMPSOMultiRunner (obj_problemDefinition); 
//		
//		try{
//			transfernonDominatedSolutions=obj_SMPSOMultiRunner.SMPSORunner();
//		}catch (Exception ex){
//		}
	}
	
	public void modifyingIssueList (){
		Random rand = new Random();
//		int solutionChoice = rand.nextInt(transfernonDominatedSolutions.size());
                int solutionChoice=0 + (int)(Math.random() * (transfernonDominatedSolutions.size()-1)); 
		
                System.out.println("@@@@@@@@@@@ simulating dev solution choice " + solutionChoice + " made from nondom sol size "+ transfernonDominatedSolutions.size());
                
		for (int i=0;i<planningIssues.size();i++){
			if (transfernonDominatedSolutions.get(solutionChoice).getVariableValueString(i).matches("1.0")){
				
				for (int j=0;j<proposedIssueData.size();j++){
					if (planningIssues.get(i).getStrKey().matches(proposedIssueData.get(j).getStrKey())){
						proposedIssueData.get(j).setOffered(true);
					}
				}
			}
		}
	}
	
	public ArrayList<DataIssueTemplate>  addNewlyOpenedIssues (){
		for (DataIssueTemplate iterator: allIssueData){
			if (iterator.getDateCreated().after(lastPlanning) && iterator.getDateCreated().before(newReplan)){
				proposedIssueData.add(iterator);
			}
		}
		
		return proposedIssueData; 
	}
	
	
	
	
}
