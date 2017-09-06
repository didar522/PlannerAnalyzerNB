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
    
    
    
    //---------------Random value initialization-------------------
    
    al_infoStructure = new ArrayList<OptVarTemplate> (getNumberOfVariables());
    
    for (int i=0;i<getNumberOfVariables();i++){
    	
    	OptVarTemplate iterator= new OptVarTemplate();
    	iterator.setValue(listEarlyOpen.get(i).getPriorityValue());
    	iterator.setCost(listEarlyOpen.get(i).getDefaultTimespent());
    	iterator.setissueType(listEarlyOpen.get(i).getIssueTypeValue());
    	   	
    	al_infoStructure.add(iterator); 
    }
    
    //---------------Random value initialization-------------------
    
      
    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(0);
      upperLimit.add(1);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);

    for (int i = 0; i < getNumberOfVariables(); i++) {
//       System.out.println(getLowerBound(i)+"--"+getUpperBound(i));
       
      }
    
    
    overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
  }

  @Override
  public void evaluate(IntegerSolution solution)  {
//    solution.setObjective(0, solution.getVariableValue(0));
//    solution.setObjective(1, solution.getVariableValue(1));
    
    
    double[] fx = new double[getNumberOfObjectives()];
    int[] xValues = new int[getNumberOfVariables()];
    
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
    	xValues[i] = solution.getVariableValue(i) ;
//    	System.out.print(xValues[i]);
    }
    
//    System.out.println("//////////////");

    fx[0] = 0;
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
//    	System.out.println(xValues[var]+" val "+ al_infoStructure.get(var).getValue());
    	fx[0] = fx[0] - xValues[var]*al_infoStructure.get(var).getValue();
//    	System.out.println("fx1 "+fx[0]);
      
    }
    
//    System.out.println("obj1 "+fx[0]);

    fx[1] = 0;
    
    double totalFtrCost=0;
    double totalBugCost=0;
    double totalImpCost=0;

    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
      
    	if (al_infoStructure.get(var).getissueType()==1){
    		totalFtrCost=(totalFtrCost+al_infoStructure.get(var).getCost())*xValues[var]; 
//    		System.out.println("ftr "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost() );
    	}
    	else if (al_infoStructure.get(var).getissueType()==2){
    		totalBugCost=(totalBugCost+al_infoStructure.get(var).getCost())*xValues[var];
//    		System.out.println("bug "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost() );
    	}
    	else if (al_infoStructure.get(var).getissueType()==3){
    		totalImpCost=(totalFtrCost+al_infoStructure.get(var).getCost())*xValues[var]; 
//    		System.out.println("imp "+al_infoStructure.get(var).getissueType()+" "+al_infoStructure.get(var).getCost() );
    	}
    }
    
    fx[1] = (Math.sqrt(Math.pow((totalCapacity*ftrRatio-totalFtrCost),2)+ Math.pow((totalCapacity*bugRatio-totalBugCost),2)+ Math.pow((totalCapacity*impRatio-totalImpCost),2)));

//    System.out.println(fx[0]+","+fx[1]);

    solution.setObjective(0, fx[0]);
    solution.setObjective(1, fx[1]);
  }
    
  
  /** EvaluateConstraints() method */
  @Override
  public void evaluateConstraints(IntegerSolution solution)  {
    int totalCost=0;
    int[] xValues = new int[getNumberOfVariables()];
    
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
    	xValues[i] = solution.getVariableValue(i) ;
    }
    
    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
        totalCost = totalCost + xValues[var]* al_infoStructure.get(var).getValue();
      }

    
//    double overallConstraintViolation = 0.0;
    int violatedConstraints = 0;
    for (int i = 0; i < getNumberOfConstraints(); i++) {
      if (totalCost>totalCapacity){
        violatedConstraints++;
      }
    }

//    overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
    numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
//    System.out.println("violatedConstraints "+ violatedConstraints);
  }
}
