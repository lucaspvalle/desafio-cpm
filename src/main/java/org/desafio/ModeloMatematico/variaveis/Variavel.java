package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class Variavel {
    public MPVariable variavel;

    public double getSolution() {
        if (this.variavel != null) {
            return this.variavel.solutionValue();
        } else {
            return 0.0;
        }
    }

    public MPVariable makeBoolVar(MPSolver solver, String name, Double peso) {
        MPVariable var = solver.makeBoolVar(name);
        solver.objective().setCoefficient(var, peso);

        return var;
    }

    public MPVariable makeNumVar(MPSolver solver, double lb, double ub, String name, Double peso) {
        MPVariable var = solver.makeNumVar(lb, ub, name);
        solver.objective().setCoefficient(var, peso);

        return var;
    }
}
