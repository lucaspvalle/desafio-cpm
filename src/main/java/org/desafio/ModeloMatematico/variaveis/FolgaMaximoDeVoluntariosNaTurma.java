package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.Parametros;

public class FolgaMaximoDeVoluntariosNaTurma extends Variavel {
    public Turma turma;

    public boolean dominio;
    public Double peso;

    public FolgaMaximoDeVoluntariosNaTurma(MPSolver solver, Turma turma) {
        this.turma = turma;
        this.dominio = true;
        this.peso = -1.0;
        this.variavel = makeNumVar(solver, 0.0, Parametros.NUM_MAXIMO_VOLUNTARIOS, toString(), this.peso);
    }

    @Override
    public String toString() {
        return "v_FolgaMaximoDeVoluntariosNaTurma{" + turma.toString() + "}";
    }
}