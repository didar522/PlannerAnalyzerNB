package OptimizerSMPSO;

import org.uma.jmetal.algorithm.impl.AbstractParticleSwarmOptimization; 
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.solutionattribute.impl.GenericSolutionAttribute;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;

/**
 * This class implements the SMPSO_Didar algorithm described in:
 SMPSO_Didar: A new PSO-based metaheuristic for multi-objective optimization
 MCDM 2009. DOI: http://dx.doi.org/10.1109/MCDM.2009.4938830
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class SMPSO_Didar extends AbstractParticleSwarmOptimization<IntegerSolution, List<IntegerSolution>> {
  private Problem<IntegerSolution> problem;

  private double c1Max;
  private double c1Min;
  private double c2Max;
  private double c2Min;
  private double r1Max;
  private double r1Min;
  private double r2Max;
  private double r2Min;
  private double weightMax;
  private double weightMin;
  private double changeVelocity1;
  private double changeVelocity2;
  
  private int getUpperBound = 1; 
  private int getLowerBound = 0; 
  private int swarmSize;
  private int maxIterations;
  private int iterations;
  
  private GenericSolutionAttribute<IntegerSolution, IntegerSolution> localBest;
  private double[][] speed;
  private JMetalRandom randomGenerator;

  private BoundedArchive<IntegerSolution> leaders;
  private Comparator<IntegerSolution> dominanceComparator;

  private MutationOperator<IntegerSolution> mutation;

  private double deltaMax[];
  private double deltaMin[];

  private SolutionListEvaluator<IntegerSolution> evaluator;

  /**
   * Constructor
   */
  public SMPSO_Didar(Problem<IntegerSolution> problem, int swarmSize, BoundedArchive<IntegerSolution> leaders,
               MutationOperator<IntegerSolution> mutationOperator, int maxIterations, double r1Min, double r1Max,
               double r2Min, double r2Max, double c1Min, double c1Max, double c2Min, double c2Max,
               double weightMin, double weightMax, double changeVelocity1, double changeVelocity2,
               SolutionListEvaluator<IntegerSolution> evaluator) {
    this.problem = problem;
    this.swarmSize = swarmSize;
    this.leaders = leaders;
    this.mutation = mutationOperator;
    this.maxIterations = maxIterations;

    this.r1Max = r1Max;
    this.r1Min = r1Min;
    this.r2Max = r2Max;
    this.r2Min = r2Min;
    this.c1Max = c1Max;
    this.c1Min = c1Min;
    this.c2Max = c2Max;
    this.c2Min = c2Min;
    this.weightMax = weightMax;
    this.weightMin = weightMin;
    this.changeVelocity1 = changeVelocity1;
    this.changeVelocity2 = changeVelocity2;

    randomGenerator = JMetalRandom.getInstance();
    this.evaluator = evaluator;

    dominanceComparator = new DominanceComparator<IntegerSolution>();
    localBest = new GenericSolutionAttribute<IntegerSolution, IntegerSolution>();
    speed = new double[swarmSize][problem.getNumberOfVariables()];
    

    deltaMax = new double[problem.getNumberOfVariables()];
    deltaMin = new double[problem.getNumberOfVariables()];
    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      deltaMax[i] = (getUpperBound - getLowerBound) / 2.0;
      deltaMin[i] = -deltaMax[i];
    }
  }

  protected void updateLeadersDensityEstimator() {
    leaders.computeDensityEstimator();
  }

  @Override protected void initProgress() {
    iterations = 1;
    updateLeadersDensityEstimator();
  }

  @Override protected void updateProgress() {
    iterations += 1;
    updateLeadersDensityEstimator();
  }

  @Override protected boolean isStoppingConditionReached() {
    return iterations >= maxIterations;
  }

  @Override protected List<IntegerSolution> createInitialSwarm() {
    List<IntegerSolution> swarm = new ArrayList<>(swarmSize);

    IntegerSolution newSolution;
    for (int i = 0; i < swarmSize; i++) {
      newSolution = problem.createSolution();
      swarm.add(newSolution);
    }

    return swarm;
  }

  protected List<IntegerSolution> evaluateSwarm(List<IntegerSolution> swarm) {
    swarm = evaluator.evaluate(swarm, problem);

    return swarm;
  }

  @Override protected void initializeLeader(List<IntegerSolution> swarm) {
    for (IntegerSolution particle : swarm) {
//        System.out.println("OptimizerSMPSO.SMPSO_Didar.initializeLeader()"+particle.getObjective(0));
//        System.out.println("OptimizerSMPSO.SMPSO_Didar.initializeLeader()---"+particle.getObjective(1));
        
        leaders.add(particle);
    }
  }

  @Override protected void initializeVelocity(List<IntegerSolution> swarm) {
    for (int i = 0; i < swarm.size(); i++) {
      for (int j = 0; j < problem.getNumberOfVariables(); j++) {
        speed[i][j] = 0.0;
        
      }
    }
  }

  @Override protected void initializeParticlesMemory(List<IntegerSolution> swarm) {
    for (IntegerSolution particle : swarm) {
      localBest.setAttribute(particle, (IntegerSolution) particle.copy());
    }
  }

//  @Override protected void updateVelocity(List<S> swarm) {
//    double r1, r2, c1, c2;
//    double wmax, wmin;
//    S bestGlobal;
//
//    for (int i = 0; i < swarm.size(); i++) {
//      S particle = (S) swarm.get(i).copy();
//      S bestParticle = (S) localBest.getAttribute(swarm.get(i)).copy();
//
//      bestGlobal = selectGlobalBest();
//
//      r1 = randomGenerator.nextDouble(r1Min, r1Max);
//      r2 = randomGenerator.nextDouble(r2Min, r2Max);
//      c1 = randomGenerator.nextDouble(c1Min, c1Max);
//      c2 = randomGenerator.nextDouble(c2Min, c2Max);
//      wmax = weightMax;
//      wmin = weightMin;
//
//      for (int var = 0; var < particle.getNumberOfVariables(); var++) {
//        speed[i][var] = velocityConstriction(constrictionCoefficient(c1, c2) * (
//                inertiaWeight(iterations, maxIterations, wmax, wmin) * speed[i][var] +
//                    c1 * r1 * (bestParticle.getVariableValue(var) - particle.getVariableValue(var)) +
//                    c2 * r2 * (bestGlobal.getVariableValue(var) - particle.getVariableValue(var))),
//            deltaMax, deltaMin, var);
//      }
//    }
//  }
//
//  @Override protected void updatePosition(List<S> swarm) {
//    for (int i = 0; i < swarmSize; i++) {
//      S particle = swarm.get(i);
//      for (int j = 0; j < particle.getNumberOfVariables(); j++) {
////        particle.setVariableValue(j, particle.getVariableValue(j) + speed[i][j]);
//        particle.setVariableValue(j, particle.getVariableValue(j) + 1);
//        if (particle.getVariableValue(j) < getLowerBound) {
//          particle.setVariableValue(j, getLowerBound);
//          speed[i][j] = speed[i][j] * changeVelocity1;
//        }
//        if (particle.getVariableValue(j) > getUpperBound) {
//          particle.setVariableValue(j, getUpperBound);
//          speed[i][j] = speed[i][j] * changeVelocity2;
//        }
//      }
//    }
//  }

  
  
  
  @Override protected void updateVelocity(List<IntegerSolution> swarm) {
    double r1, r2, c1, c2;
    double wmax, wmin;
    IntegerSolution bestGlobal;

    for (int i = 0; i < swarm.size(); i++) {
      IntegerSolution particle = (IntegerSolution) swarm.get(i).copy();
      IntegerSolution bestParticle = (IntegerSolution) localBest.getAttribute(swarm.get(i)).copy();

      bestGlobal = selectGlobalBest();

      r1 = randomGenerator.nextDouble(r1Min, r1Max);
      r2 = randomGenerator.nextDouble(r2Min, r2Max);
      c1 = randomGenerator.nextDouble(c1Min, c1Max);
      c2 = randomGenerator.nextDouble(c2Min, c2Max);
      wmax = weightMax;
      wmin = weightMin;

      for (int var = 0; var < particle.getNumberOfVariables(); var++) {
        speed[i][var]= velocityConstriction(constrictionCoefficient(c1, c2) * (
                   (c2 * r2 * (bestGlobal.getVariableValue(var) - particle.getVariableValue(var))) +
                           inertiaWeight(iterations, maxIterations, wmax, wmin) * speed[i][var] +
                           (c1 * r1 * (bestParticle.getVariableValue(var) - particle.getVariableValue(var)))),
                                deltaMax, deltaMin, var);
 
      }
    }
  }

  @Override protected void updatePosition(List<IntegerSolution> swarm) {
    for (int i = 0; i < swarmSize; i++) {
      IntegerSolution particle = swarm.get(i);
      for (int j = 0; j < particle.getNumberOfVariables(); j++) {
//        particle.setVariableValue(j, particle.getVariableValue(j) + speed[i][j]);
        double randomValueCompare = deltaMin[j] + (int)(Math.random() * (1+deltaMax[j])); 
        double particleUpdatedValue = particle.getVariableValue(j) + speed[i][j]; 
        
        if (randomValueCompare<particleUpdatedValue){
            particle.setVariableValue(j, 0);
        }
        else {
            particle.setVariableValue(j, 1);
        }
      }
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  @Override protected void perturbation(List<IntegerSolution> swarm) {
    for (int i = 0; i < swarm.size(); i++) {
      if ((i % 6) == 0) {
        mutation.execute(swarm.get(i));
      }
    }
  }

  @Override protected void updateLeaders(List<IntegerSolution> swarm) {
    for (IntegerSolution particle : swarm) {
      leaders.add((IntegerSolution)particle.copy());
    }
  }

  @Override protected void updateParticlesMemory(List<IntegerSolution> swarm) {
    for (int i = 0; i < swarm.size(); i++) {
      int flag = dominanceComparator.compare(swarm.get(i), localBest.getAttribute(swarm.get(i)));
      if (flag != 1) {
        IntegerSolution particle = (IntegerSolution) swarm.get(i).copy();
        localBest.setAttribute(swarm.get(i), particle);
      }
    }
  }

  @Override public List<IntegerSolution> getResult() {
    return leaders.getSolutionList();
  }

  protected IntegerSolution selectGlobalBest() {
    IntegerSolution one, two;
    IntegerSolution bestGlobal;
    int pos1 = randomGenerator.nextInt(0, leaders.getSolutionList().size() - 1);
    int pos2 = randomGenerator.nextInt(0, leaders.getSolutionList().size() - 1);
    one = leaders.getSolutionList().get(pos1);
    two = leaders.getSolutionList().get(pos2);

    if (leaders.getComparator().compare(one, two) < 1) {
      bestGlobal = (IntegerSolution) one.copy();
    } else {
      bestGlobal = (IntegerSolution) two.copy();
    }

    return bestGlobal;
  }

  private double velocityConstriction(double v, double[] deltaMax, double[] deltaMin,
                                      int variableIndex) {
    double result;

    double dmax = deltaMax[variableIndex];
    double dmin = deltaMin[variableIndex];

    result = v;

    if (v > dmax) {
      result = dmax;
    }

    if (v < dmin) {
      result = dmin;
    }

    return result;
  }

  private double constrictionCoefficient(double c1, double c2) {
    double rho = c1 + c2;
    if (rho <= 4) {
      return 1.0;
    } else {
      return 2 / (2 - rho - Math.sqrt(Math.pow(rho, 2.0) - 4.0 * rho));
    }
  }

  private double inertiaWeight(int iter, int miter, double wma, double wmin) {
    return wma;
  }

  @Override public String getName() {
    return "SMPSO" ;
  }

  @Override public String getDescription() {
    return "Speed contrained Multiobjective PSO" ;
  }
  
  /* Getters */
  public int getSwarmSize() {
    return swarmSize;
  }
  
  public int getMaxIterations() {
    return maxIterations;
  }
  
  public int getIterations() {
    return iterations;
  }
  
  /* Setters */
  public void setIterations(int iterations) {
    this.iterations = iterations;
  }
}
