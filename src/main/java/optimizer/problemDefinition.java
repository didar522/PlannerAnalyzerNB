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
  int intTotalThemeSetSize=0; 

  /**
   * Constructor.
   * Creates a default instance of the problem Tanaka
   */
  public problemDefinition(ArrayList<DataIssueTemplate> listEarlyOpen, double totalCapacity) {
    this.listEarlyOpen = listEarlyOpen; 
    this.totalCapacity = totalCapacity;   
    	  
    setNumberOfVariables(listEarlyOpen.size());
    setNumberOfObjectives(4);
    setNumberOfConstraints(1);
    setName("Planning") ;
    
    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;
    
    overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
    
    //---------------Value & Limit initialization-------------------
    
    al_infoStructure = new ArrayList<OptVarTemplate> (getNumberOfVariables());
    
    for (int i=0;i<getNumberOfVariables();i++){
    	
    	OptVarTemplate iterator= new OptVarTemplate();
    	iterator.setValue(listEarlyOpen.get(i).getPriorityValue());
        int randomThemeWeight = 1 + (int)(Math.random()*9);
        if (listEarlyOpen.get(i).getIntThemeValue()>=7){
            intTotalThemeSetSize++; 
        }
        iterator.setThemevalue(listEarlyOpen.get(i).getIntThemeValue());
    	iterator.setCost(listEarlyOpen.get(i).getDefaultTimespent());
    	iterator.setissueType(listEarlyOpen.get(i).getIssueTypeValue());

    	al_infoStructure.add(iterator); 
    }
    
    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(0);
      upperLimit.add(1);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);

    //---------------Value & Limit initialization-------------------
 
//    overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
//    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
  }

  @Override
  public void evaluate(IntegerSolution solution)  {
    int intOfferedFromThemeSet=0; 
    double dblThemeCoverage=0;
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
//  
    double totalFtrValue=0;
    double totalBugValue=0;
    double totalImpValue=0;
    double totalValue=0;
    
    fx[0] = 0;
    fx[1] = 0;
    fx[2] = 0;
    
    
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
      
    	if (al_infoStructure.get(var).getissueType()==1){
                totalFtrCost=(totalFtrCost+al_infoStructure.get(var).getCost()*xValues[var]); 
                totalFtrValue=(totalFtrValue-al_infoStructure.get(var).getValue()*xValues[var]); 
    	}
    	else if (al_infoStructure.get(var).getissueType()==2){
                totalBugCost=(totalBugCost+al_infoStructure.get(var).getCost()*xValues[var]);
                totalBugValue=(totalBugValue-al_infoStructure.get(var).getValue()*xValues[var]);
        }
    	else if (al_infoStructure.get(var).getissueType()==3){
                totalImpCost=(totalImpCost+al_infoStructure.get(var).getCost()*xValues[var]); 
                totalImpValue=(totalImpValue-al_infoStructure.get(var).getValue()*xValues[var]);
        }
    }
    
    totalCost = totalFtrCost+totalBugCost+totalImpCost; 
    totalValue = totalFtrValue+totalBugValue+totalImpValue; 
    
    double totalCapacityCompare=1; 
    if (totalCost>totalCapacity){
         totalCapacityCompare = Math.abs(totalCapacity-totalCost); 
    }
    
    fx[0] = totalFtrValue/totalCapacityCompare;
    fx[1] = totalBugValue/totalCapacityCompare;
    fx[2] = totalImpValue/totalCapacityCompare;
    
    
    double totalThemeValue=0;
    fx[3] = 0;
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
          totalThemeValue = totalThemeValue - (xValues[var]*al_infoStructure.get(var).getThemevalue());
          if (al_infoStructure.get(var).getThemevalue()>=7){
              intOfferedFromThemeSet++; 
          }
    }
    fx[3]=totalThemeValue/totalCapacityCompare; 
    dblThemeCoverage = (intOfferedFromThemeSet/intTotalThemeSetSize)*100; 
    
//    This need to be on 
//    System.out.println("Population,"+fx[0]+","+fx[1]+","+totalValue+","+totalDistance+","+proposedFtrCostRatio+","+proposedBugCostRatio+","+proposedImpCostRatio+","+totalCost+","+totalCapacity);

    System.out.println("Population values: "+totalValue+","+totalThemeValue+","+totalCost+","+totalFtrValue+","+totalBugValue+","+totalImpValue+","+totalCapacityCompare+","+dblThemeCoverage);
    System.out.println("Population Objectives: "+fx[0]+","+fx[1]+","+fx[2]+","+fx[3]);

    solution.setObjective(0, fx[0]);
    solution.setObjective(1, fx[1]);
    solution.setObjective(2, fx[2]);
    solution.setObjective(3, fx[3]);
    
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
