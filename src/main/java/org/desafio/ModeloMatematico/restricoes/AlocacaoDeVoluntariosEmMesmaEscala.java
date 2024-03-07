package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

//TODO: funciona apenas se as aloca��es dos volunt�rios possu�rem o mesmo dom�nio de prefer�ncias de per�odos
//      se o volunt�rio n�o possuir nenhuma aloca��o, a aloca��o do volunt�rio na mesma escala deveria ser livre
public class AlocacaoDeVoluntariosEmMesmaEscala {
    public AlocacaoDeVoluntariosEmMesmaEscala(MPSolver solver,
                                              Voluntario voluntario,
                                              Voluntario voluntarioNaMesmaEscala,
                                              PreferenciaDePeriodo periodo,
                                              ArrayList<AlocacaoDeVoluntarioNaTurma> possiveisAlocacoesDoVoluntario,
                                              ArrayList<AlocacaoDeVoluntarioNaTurma> possiveisAlocacoesDoVoluntarioNaMesmaEscala) {
        if (possiveisAlocacoesDoVoluntario.isEmpty() | possiveisAlocacoesDoVoluntarioNaMesmaEscala.isEmpty()) {
            return;
        }

        double inf = Double.POSITIVE_INFINITY;
        MPConstraint cAlocacaoNaMesmaEscala = solver.makeConstraint(0, inf,
                "c_AlocacaoNaMesmaEscala{" + voluntario + ", " + voluntarioNaMesmaEscala + ", " + periodo + "}");

        for (AlocacaoDeVoluntarioNaTurma alocacao : possiveisAlocacoesDoVoluntario) {
            cAlocacaoNaMesmaEscala.setCoefficient(alocacao.variavel, 1);
        }

        for (AlocacaoDeVoluntarioNaTurma alocacao : possiveisAlocacoesDoVoluntarioNaMesmaEscala) {
            cAlocacaoNaMesmaEscala.setCoefficient(alocacao.variavel, -1);
        }
    }
}
