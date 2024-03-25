package org.desafio.ModeloMatematico;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.dao.AlocacaoDao;

import java.util.HashMap;

public class Variaveis {

    public MPSolver solver;
    HashMap<MPVariable, Double> variaveis;
    public AlocacaoDao alocacoes;

    public MPVariable makeBoolVar(String name, Double peso) {
        MPVariable variavel = solver.makeBoolVar(name);
        addVariavel(variavel, peso);

        return variavel;
    }

    public MPVariable makeNumVar(double lb, double ub, String name, Double peso) {
        MPVariable variavel = solver.makeNumVar(lb, ub, name);
        addVariavel(variavel, peso);

        return variavel;
    }

    private void addVariavel(MPVariable variavel, Double peso) {
        variaveis.put(variavel, peso);
    }

    public void adicionarVariaveisNaFuncaoObjetivo() {
        variaveis.forEach((var, peso) -> solver.objective().setCoefficient(var, peso));
        solver.objective().setMaximization();
    }

    public Variaveis(MPSolver solver, VoluntarioDao voluntarios, TurmaDao turmas) {
        this.solver = solver;
        this.variaveis = new HashMap<>();
        this.alocacoes = new AlocacaoDao(this, voluntarios, turmas);
    }
}
