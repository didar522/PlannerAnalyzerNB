/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptimizerSMPSO;

import org.uma.jmetal.problem.Problem;

import java.io.Serializable;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 * Created by Antonio J. Nebro on 30/05/14.
 */

public interface SolutionListEvaluator_Didar<S extends Solution<?>> extends Serializable {
  List<S> evaluate(List<S> solutionList, Problem<S> problem) ;
  void shutdown() ;
}
