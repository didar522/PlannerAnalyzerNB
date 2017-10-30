package OptimizerSMPSO;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @param <S>
 */
public class SMPSOBuilder_Didar implements AlgorithmBuilder<SMPSO_Didar> {
  public enum SMPSOVariant {SMPSO, Measures}

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

  private int swarmSize;
  private int maxIterations;

  protected int archiveSize;

  protected MutationOperator<IntegerSolution> mutationOperator;

  protected BoundedArchive<IntegerSolution> leaders;

  protected SolutionListEvaluator<IntegerSolution> evaluator;

  protected SMPSOVariant variant ;

  public SMPSOBuilder_Didar(Problem<IntegerSolution> problem, BoundedArchive<IntegerSolution> leaders) {
    this.problem = problem;
    this.leaders = leaders;

    swarmSize = 100;
    maxIterations = 250;

    r1Max = 1.0;
    r1Min = 0.0;
    r2Max = 1.0;
    r2Min = 0.0;
    c1Max = 2.5;
    c1Min = 1.5;
    c2Max = 2.5;
    c2Min = 1.5;
    weightMax = 0.1;
    weightMin = 0.1;
    changeVelocity1 = 0.5;
    changeVelocity2 = -0.5;

    mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(), 20.0) ;
    evaluator = new SequentialSolutionListEvaluator<IntegerSolution>() ;

    this.variant = SMPSOVariant.SMPSO ;

  }

  /* Getters */
  public int getSwarmSize() {
    return swarmSize;
  }

  public int getMaxIterations() {
    return maxIterations;
  }

  public double getR1Max() {
    return r1Max;
  }

  public double getR1Min() {
    return r1Min;
  }

  public double getR2Max() {
    return r2Max;
  }

  public double getR2Min() {
    return r2Min;
  }

  public double getC1Max() {
    return c1Max;
  }

  public double getC1Min() {
    return c1Min;
  }

  public double getC2Max() {
    return c2Max;
  }

  public double getC2Min() {
    return c2Min;
  }

  public MutationOperator<IntegerSolution> getMutation() {
    return mutationOperator;
  }

  public double getWeightMax() {
    return weightMax;
  }

  public double getWeightMin() {
    return weightMin;
  }

  public double getChangeVelocity1() {
    return changeVelocity1;
  }

  public double getChangeVelocity2() {
    return changeVelocity2;
  }

  /* Setters */
  public SMPSOBuilder_Didar setSwarmSize(int swarmSize) {
    this.swarmSize = swarmSize;

    return this;
  }

  public SMPSOBuilder_Didar setMaxIterations(int maxIterations) {
    this.maxIterations = maxIterations;

    return this;
  }

  public SMPSOBuilder_Didar setMutation(MutationOperator<IntegerSolution> mutation) {
    mutationOperator = mutation;

    return this;
  }

  public SMPSOBuilder_Didar setC1Max(double c1Max) {
    this.c1Max = c1Max;

    return this;
  }

  public SMPSOBuilder_Didar setC1Min(double c1Min) {
    this.c1Min = c1Min;

    return this;
  }

  public SMPSOBuilder_Didar setC2Max(double c2Max) {
    this.c2Max = c2Max;

    return this;
  }

  public SMPSOBuilder_Didar setC2Min(double c2Min) {
    this.c2Min = c2Min;

    return this;
  }

  public SMPSOBuilder_Didar setR1Max(double r1Max) {
    this.r1Max = r1Max;

    return this;
  }

  public SMPSOBuilder_Didar setR1Min(double r1Min) {
    this.r1Min = r1Min;

    return this;
  }

  public SMPSOBuilder_Didar setR2Max(double r2Max) {
    this.r2Max = r2Max;

    return this;
  }

  public SMPSOBuilder_Didar setR2Min(double r2Min) {
    this.r2Min = r2Min;

    return this;
  }

  public SMPSOBuilder_Didar setWeightMax(double weightMax) {
    this.weightMax = weightMax;

    return this;
  }

  public SMPSOBuilder_Didar setWeightMin(double weightMin) {
    this.weightMin = weightMin;

    return this;
  }

  public SMPSOBuilder_Didar setChangeVelocity1(double changeVelocity1) {
    this.changeVelocity1 = changeVelocity1;

    return this;
  }

  public SMPSOBuilder_Didar setChangeVelocity2(double changeVelocity2) {
    this.changeVelocity2 = changeVelocity2;

    return this;
  }

  public SMPSOBuilder_Didar setRandomGenerator(PseudoRandomGenerator randomGenerator) {
    JMetalRandom.getInstance().setRandomGenerator(randomGenerator);

    return this;
  }

  public SMPSOBuilder_Didar setSolutionListEvaluator(SolutionListEvaluator<IntegerSolution> evaluator) {
    this.evaluator = evaluator ;

    return this ;
  }

  public SMPSOBuilder_Didar setVariant(SMPSOVariant variant) {
    this.variant = variant;

    return this;
  }

  public SMPSO_Didar build() {
    if (variant.equals(SMPSOVariant.SMPSO)) {
      return new SMPSO_Didar(problem, swarmSize, leaders, mutationOperator, maxIterations, r1Min, r1Max,
          r2Min, r2Max, c1Min, c1Max, c2Min, c2Max, weightMin, weightMax, changeVelocity1,
          changeVelocity2, evaluator);
    } else {
      return new SMPSOMeasures_Didar(problem, swarmSize, leaders, mutationOperator, maxIterations, r1Min, r1Max,
          r2Min, r2Max, c1Min, c1Max, c2Min, c2Max, weightMin, weightMax, changeVelocity1,
          changeVelocity2, evaluator);
    }
  }

  /*
   * Getters
   */
  public Problem<IntegerSolution> getProblem() {
    return problem;
  }

  public int getArchiveSize() {
    return archiveSize;
  }

  public MutationOperator<IntegerSolution> getMutationOperator() {
    return mutationOperator;
  }

  public BoundedArchive<IntegerSolution> getLeaders() {
    return leaders;
  }

  public SolutionListEvaluator<IntegerSolution> getEvaluator() {
    return evaluator;
  }
}



