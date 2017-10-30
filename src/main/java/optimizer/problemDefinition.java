package optimizer;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import dataTemplates.DataIssueTemplate;
import dataTemplates.OptVarTemplate;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.DoubleSolution;

/**
 * Class representing problem Tanaka
 */
@SuppressWarnings("serial")
public class problemDefinition extends AbstractIntegerProblem implements ConstrainedProblem<IntegerSolution> {
  public OverallConstraintViolation<IntegerSolution> overallConstraintViolationDegree ;
  public NumberOfViolatedConstraints<IntegerSolution> numberOfViolatedConstraints ;
  
  public ArrayList <OptVarTemplate> al_infoStructure;
  public ArrayList<DataIssueTemplate> listEarlyOpen = new ArrayList<DataIssueTemplate>();
  public ArrayList<DataIssueTemplate> listInClose = new ArrayList<DataIssueTemplate>();  
  
  double totalCapacity=0; 
  int bugRatio=0;
  int ftrRatio=0;
  int impRatio=0; 
  

  /**
   * Constructor.
   * Creates a default instance of the problem Tanaka
   */
  public problemDefinition(ArrayList<DataIssueTemplate> listEarlyOpen, double totalCapacity, int bugRatio, int ftrRatio, int impRatio) {
    this.listEarlyOpen = listEarlyOpen; 
    this.totalCapacity = totalCapacity;   
    
    this.bugRatio = bugRatio; 
    this.ftrRatio = ftrRatio; 
    this.impRatio = impRatio;
	  
    setNumberOfVariables(listEarlyOpen.size());
    setNumberOfObjectives(2);
    setNumberOfConstraints(1);
    setName("Planning") ;
    
    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;
    
    overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
    
    //---------------Random value initialization-------------------
    
    al_infoStructure = new ArrayList<OptVarTemplate> (getNumberOfVariables());
    
    for (int i=0;i<getNumberOfVariables();i++){
    	
    	OptVarTemplate iterator= new OptVarTemplate();
    	iterator.setValue(listEarlyOpen.get(i).getPriorityValue());
    	iterator.setCost(listEarlyOpen.get(i).getDefaultTimespent());
    	iterator.setissueType(listEarlyOpen.get(i).getIssueTypeValue());
        
//        System.out.println("setting cost" + listEarlyOpen.get(i).getDefaultTimespent());
//        System.out.println("after set cost" + iterator.getCost());
    	   	
    	al_infoStructure.add(iterator); 
    }
    
    //---------------Random value initialization-------------------
    
      
    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(0);
      upperLimit.add(1);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);

    
//    overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
//    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
  }

  @Override
  public void evaluate(IntegerSolution solution)  {

    double[] fx = new double[getNumberOfObjectives()];
    int[] xValues = new int[getNumberOfVariables()];
    
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
    	xValues[i] = solution.getVariableValue(i) ;
    	System.out.print(xValues[i]+"-");
    }
    
    double totalFtrCost=0;
    double totalBugCost=0;
    double totalImpCost=0;
    double totalCost=0;
    
    double proposedFtrCostRatio =0; 
    double proposedBugCostRatio =0; 
    double proposedImpCostRatio =0; 

    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
      
    	if (al_infoStructure.get(var).getissueType()==1){
//    		System.out.println("Old total ftr cost"+ totalFtrCost);
                totalFtrCost=(totalFtrCost+al_infoStructure.get(var).getCost()*xValues[var]); 
//    		System.out.println("ftr "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost()+ " "+ totalFtrCost);
    	}
    	else if (al_infoStructure.get(var).getissueType()==2){
//            System.out.println("Old total bug cost"+ totalBugCost);	
            totalBugCost=(totalBugCost+al_infoStructure.get(var).getCost()*xValues[var]);
//    	    System.out.println("bug "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost()+ " "+ totalBugCost );
    	}
    	else if (al_infoStructure.get(var).getissueType()==3){
//    		System.out.println("Old total imp cost"+ totalImpCost);
                totalImpCost=(totalImpCost+al_infoStructure.get(var).getCost()*xValues[var]); 
//    		System.out.println("imp "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost()+ " "+ totalImpCost );
    	}
    }
    
    totalCost = totalFtrCost+totalBugCost+totalImpCost; 
    
    
    double totalCapacityCompare=1; 
    if (totalCost>totalCapacity){
         totalCapacityCompare = Math.abs(totalCapacity-totalCost); 
    }


    fx[0] = 0;
    double totalValue=0; 
    double pmPreference=0; 
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
    	
        if (al_infoStructure.get(var).getissueType()==1){
            pmPreference = Math.ceil(ftrRatio/20); 
        }
        else if (al_infoStructure.get(var).getissueType()==2){
            pmPreference = Math.ceil(bugRatio/20); 
        }
        else if (al_infoStructure.get(var).getissueType()==3){
            pmPreference = Math.ceil(impRatio/20); 
        }
        
        totalValue = totalValue - (xValues[var]*al_infoStructure.get(var).getValue())-(xValues[var]*pmPreference);
    }
    fx[0]=totalValue/totalCapacityCompare; 
//    fx[0]=totalValue; 
    
//    System.out.println("Evaluating fx0 "+fx[0]);
    
 
    fx[1] = 0;
    double totalDistance = Math.sqrt(Math.pow((totalCapacity*ftrRatio/100-totalFtrCost),2)+ Math.pow((totalCapacity*bugRatio/100-totalBugCost),2)+ Math.pow((totalCapacity*impRatio/100-totalImpCost),2));
    fx[1] = totalDistance*totalCapacityCompare; 
    
    
    
    
    proposedFtrCostRatio = totalFtrCost / (totalFtrCost+totalBugCost+totalImpCost)*100;  
    proposedBugCostRatio =totalBugCost / (totalFtrCost+totalBugCost+totalImpCost)*100; 
    proposedImpCostRatio= totalImpCost / (totalFtrCost+totalBugCost+totalImpCost)*100;
    
    
    
//    fx[1] = totalDistance;
    
    
//    System.out.println("Total Eq Ftr--"+totalCapacity+"*"+ftrRatio+"-"+totalFtrCost);
//    System.out.println("Total Eq Bug--"+totalCapacity+"*"+bugRatio+"-"+totalBugCost);
//    System.out.println("Total Eq Imp--"+totalCapacity+"*"+impRatio+"-"+totalImpCost);
//    
//    System.out.println("Evaluating fx1 "+fx[0]);
//
// 
//      
//    System.out.println("Total capacity "+ totalCapacity);
//    System.out.println("Total cost "+ (totalFtrCost+totalBugCost+totalImpCost));
      
//      if (totalCost>totalCapacity){
//          fx[1]=fx[1]*3;
//          fx[1]=999999999;
//      }
      
      
      
//      System.out.println("Evaluating correction fx0 "+fx[1]);
//      System.out.println("Evaluating correction  fx1 "+fx[1]);


    System.out.println("Population,"+fx[0]+","+fx[1]+","+totalValue+","+totalDistance+","+proposedFtrCostRatio+","+proposedBugCostRatio+","+proposedImpCostRatio+","+totalCost+","+totalCapacity);
//    System.out.println("Population,"+fx[0]+","+totalValue+","+totalCost+","+totalCapacity);

    solution.setObjective(0, fx[0]);
    solution.setObjective(1, fx[1]);
  }
    
  
  
  
  /** EvaluateConstraints() method */
  @Override
  public void evaluateConstraints(IntegerSolution solution)  {
    double totalCost=0;
    int[] xValues = new int[getNumberOfVariables()];
    double[] constraint = new double[this.getNumberOfConstraints()];
    
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
    	xValues[i] = solution.getVariableValue(i) ;
    }
    
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
        totalCost = totalCost + xValues[var]* al_infoStructure.get(var).getCost();
    }

//    System.out.println("Evaluating constraints--------");
//    System.out.println("total cost"+ totalCost + "total capacity" + totalCapacity);
    
 
    constraint[0]=Math.abs(totalCapacity-totalCost); 

    double overallConstraintViolation = 0.0;
    int violatedConstraints = 0;

    for (int i = 0; i < getNumberOfConstraints(); i++) {
      if (constraint[i]>0){
        overallConstraintViolation += constraint[i];
        violatedConstraints++;
//        System.out.println("Violated constraints "+violatedConstraints + "total cost"+ totalCost + "total capacity" + totalCapacity);
      }
    }

    overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
    numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
//    System.out.println("violatedConstraints "+ violatedConstraints);
  }
}
