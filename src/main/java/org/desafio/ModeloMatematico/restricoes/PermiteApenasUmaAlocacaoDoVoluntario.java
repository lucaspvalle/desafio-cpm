package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

public class PermiteApenasUmaAlocacaoDoVoluntario {

    public PermiteApenasUmaAlocacaoDoVoluntario(MPSolver solver, Voluntario voluntario, ArrayList<AlocacaoDeVoluntarioNaTurma> possiveisAlocacoesDoVoluntario) {
        if (possiveisAlocacoesDoVoluntario.isEmpty()) {
            return;
        }

        MPConstraint cUnicaAlocacao = solver.makeConstraint(0, 1, "c_UnicaAlocacao{" + voluntario + "}");

        for (AlocacaoDeVoluntarioNaTurma alocacao : possiveisAlocacoesDoVoluntario) {
            cUnicaAlocacao.setCoefficient(alocacao.variavel, 1);
        }
    }
}
