package planning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.solution.DoubleSolution;

import dataTemplates.DataIssueTemplate;
import optimizer.NSGAIIMultiRunner;
import optimizer.problemDefinition;

public class relRePlanning {
	
	public ArrayList<DataIssueTemplate> allIssueData; 
	public ArrayList<DataIssueTemplate> proposedIssueData; 
	public ArrayList<DataIssueTemplate> planningIssues=new ArrayList<DataIssueTemplate> (); 
	
	List<DoubleSolution> transfernonDominatedSolutions;
	
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
					Date tmpDiffDate =null; 
					if (iterator.getDateUpdated().before(lastPlanning)){
						tmpDiffDate = lastPlanning; 
					}
					else {
						tmpDiffDate=iterator.getDateUpdated();
					}
					
					spenteffort += iterator.getTimespent(tmpDiffDate); 
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
		problemDefinition obj_problemDefinition = new problemDefinition (planningIssues, spenteffort, bugRatio, ftrRatio, impRatio); 
		NSGAIIMultiRunner obj_NSGAIIMultiRunner = new NSGAIIMultiRunner (obj_problemDefinition); 
		
		try{
			transfernonDominatedSolutions=obj_NSGAIIMultiRunner.NSGARunner();
		}catch (Exception ex){
		}
	}
	
	public void modifyingIssueList (){
		Random rand = new Random();
		int solutionChoice = rand.nextInt(transfernonDominatedSolutions.size());
		
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
