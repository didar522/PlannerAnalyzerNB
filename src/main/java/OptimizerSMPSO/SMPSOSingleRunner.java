/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptimizerSMPSO;

/**
 *
 * @author Didar
 */
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSOBuilder;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.impl.MersenneTwisterGenerator;

import java.util.List;
import optimizer.problemDefinition;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.problem.IntegerProblem;
import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printFinalSolutionSet;
import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printQualityIndicators;
import org.uma.jmetal.solution.IntegerSolution;

/**
 * Class for configuring and running the SMPSO algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class SMPSOSingleRunner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments. The first (optional) argument specifies
   *             the problem to solve.
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.SMPSOSingleRunner problemName [referenceFront]
   */
  problemDefinition obj_problemDefinition;  
    
    
    
  public SMPSOSingleRunner (problemDefinition tmp_problemDefinition){
	this.obj_problemDefinition = tmp_problemDefinition; 
}
    
  public List<IntegerSolution> SMPSORunner() throws Exception {
    IntegerProblem problem;
     
    Algorithm<List<IntegerSolution>> algorithm;
    MutationOperator<IntegerSolution> mutation;

//    String referenceParetoFront = "" ;

//    String problemName ;
//    if (args.length == 1) {
//      problemName = args[0];
//    } else if (args.length == 2) {
//      problemName = args[0] ;
//      referenceParetoFront = args[1] ;
//    } else {
//      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT4";
//      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT4.pf" ;
//    }

    problem = obj_problemDefinition;

    BoundedArchive<IntegerSolution> archive = new CrowdingDistanceArchive<IntegerSolution>(100) ;

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0 ;
    mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex) ;

    algorithm = new SMPSOBuilder_Didar(problem, archive)
        .setMutation(mutation)
        .setMaxIterations(250)
        .setSwarmSize(100)
        .setRandomGenerator(new MersenneTwisterGenerator())
        .setSolutionListEvaluator(new SequentialSolutionListEvaluator<IntegerSolution>())
        .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute();

    List<IntegerSolution> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    
    for (int i=0; i<population.size();i++){
        System.out.print("solution : ");
        for (int j=0;j<population.get(i).getNumberOfVariables();j++){
             System.out.print(population.get(i).getVariableValueString(j)+" ");
        }
        System.out.println(); 
        System.out.println("Obj1,2 : "+population.get(i).getObjective(0)+" , "+population.get(i).getObjective(1));
//        System.out.println("Obj1,2 : "+population.get(i).getObjective(0)+" , "+population.get(i).getObjective(0));
    }
//    if (!referenceParetoFront.equals("")) {
//      printQualityIndicators(population, referenceParetoFront) ;
//    }

    return population; 
  }
}
