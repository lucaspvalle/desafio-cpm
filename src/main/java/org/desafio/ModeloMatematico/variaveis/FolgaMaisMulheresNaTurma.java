package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;

public class FolgaMaisMulheresNaTurma extends Variavel {
    public Turma turma;

    public boolean dominio;
    public Double peso;

    public FolgaMaisMulheresNaTurma(MPSolver solver, Turma turma) {
        this.turma = turma;
        this.dominio = true;
        this.peso = -1.0;
        this.variavel = makeNumVar(solver, 0.0, 100.0, toString(), this.peso);
    }

    @Override
    public String toString() {
        return "v_FolgaMaisMulheresNaTurma{" + turma.toString() + "}";
    }
}