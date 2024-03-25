package org.desafio.ModeloMatematico;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.restricoes.*;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

public class Restricoes {
    private void criarRestricoesDeTurmas(MPSolver solver, Variaveis variaveis, TurmaDao turmas) {
        for (Turma turma : turmas.getAllTurmas()) {
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma = variaveis.alocacoes.getPossiveisAlocacoesDeUmaTurma(turma);
            if (alocacoesNaTurma.isEmpty()) {
                continue;
            }

            new QuantidadeDeVoluntariosEmTurma(solver, variaveis, turma, alocacoesNaTurma);
            new ProporcaoDeGeneroEmTurmas(solver, variaveis, turma, alocacoesNaTurma);
            new ProporcaoDeExperienciaEmTurmas(solver, variaveis, turma, alocacoesNaTurma);
            //TODO: proporção entre grau de comprometimento
        }
    }

    public void criarRestricoesDeVoluntarios(MPSolver solver, Variaveis variaveis, VoluntarioDao voluntarios) {
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario = variaveis.alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntario);
            if (alocacoesDoVoluntario.isEmpty()) {
                continue;
            }

            new PermiteApenasUmaAlocacaoDoVoluntario(solver, voluntario, alocacoesDoVoluntario);
            criarRestricoesDeVoluntariosNaMesmaEscala(solver, variaveis, voluntario, alocacoesDoVoluntario);
        }
    }

    public void criarRestricoesDeVoluntariosNaMesmaEscala(
            MPSolver solver, Variaveis variaveis, Voluntario voluntario, ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario) {
        for (Voluntario voluntarioNaEscala : voluntario.getVoluntariosNaMesmaEscala()) {
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaMesmaEscala =
                    variaveis.alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntarioNaEscala);
            if (alocacoesDoVoluntarioNaMesmaEscala.isEmpty()) {
                continue;
            }

            new AlocacaoDeVoluntariosEmMesmaEscala(
                    solver, variaveis, voluntario, voluntarioNaEscala, alocacoesDoVoluntario, alocacoesDoVoluntarioNaMesmaEscala);
        }
    }

    public Restricoes(MPSolver solver, Variaveis variaveis, VoluntarioDao voluntarios, TurmaDao turmas) {
        criarRestricoesDeTurmas(solver, variaveis, turmas);
        criarRestricoesDeVoluntarios(solver, variaveis, voluntarios);
    }
}
