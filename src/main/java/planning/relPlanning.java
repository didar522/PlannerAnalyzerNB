package planning;

import OptimizerSMPSO.SMPSOMultiRunner;
import OptimizerSMPSO.SMPSOSingleRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.solution.DoubleSolution;

import dataPreprocess.Preprocessing;
import dataTemplates.DataIssueTemplate;
import optimizer.NSGAIIMultiRunner;
import optimizer.problemDefinition;
import org.uma.jmetal.solution.IntegerSolution;

public class relPlanning {
	
	double dblAvailableCapacity=0;
	int bugRatio=0, ftrRatio=0, impRatio=0; 
	
	problemDefinition obj_problemDefinition; 
	NSGAIIMultiRunner obj_NSGAIIMultiRunner;
	
	ArrayList<DataIssueTemplate> backlogIssueData = new ArrayList<DataIssueTemplate>();
	public List<DoubleSolution> transfernonDominatedSolutions;
//	public List<IntegerSolution> transfernonDominatedSolutions;
	
	public void performRelPlanning (ArrayList<DataIssueTemplate> tmp_backlogIssueData, double tmp_dblAvailableCapacity, int bugRatio, int ftrRatio, int impRatio) throws Exception {
		this.backlogIssueData = tmp_backlogIssueData; 
		this.dblAvailableCapacity = tmp_dblAvailableCapacity; 
		
		obj_problemDefinition = new problemDefinition (backlogIssueData, dblAvailableCapacity, bugRatio, ftrRatio, impRatio); 


		NSGAIIMultiRunner obj_NSGAIIMultiRunner = new NSGAIIMultiRunner (obj_problemDefinition); 
		transfernonDominatedSolutions=obj_NSGAIIMultiRunner.NSGARunner();
                
//              SMPSOMultiRunner obj_SMPSOMultiRunner = new SMPSOMultiRunner (obj_problemDefinition); 
//		transfernonDominatedSolutions=obj_SMPSOMultiRunner.SMPSORunner();
                
//              SMPSOSingleRunner obj_SMPSOSingleRunner = new SMPSOSingleRunner (obj_problemDefinition); 
//		transfernonDominatedSolutions=obj_SMPSOSingleRunner.SMPSORunner();
		
	}
	
	public ArrayList<DataIssueTemplate> identifyRandOffered (){
		Random rand = new Random();
		int solutionChoice = rand.nextInt(transfernonDominatedSolutions.size());

		for (int i=0;i<backlogIssueData.size();i++){
			if (transfernonDominatedSolutions.get(solutionChoice).getVariableValueString(i).matches("1.0")){
				backlogIssueData.get(i).setOffered(true);
			}
		}
		
		return backlogIssueData; 
	}
	
	public ArrayList<DataIssueTemplate> identifyOfferedforChoice (int choice){
		for (int i=0;i<backlogIssueData.size();i++){
                    backlogIssueData.get(i).setOffered(false);
                    if (transfernonDominatedSolutions.get(choice).getVariableValueString(i).matches("1.0")){
				backlogIssueData.get(i).setOffered(true);
			}
		}
		
		return backlogIssueData; 
	}
	
}
