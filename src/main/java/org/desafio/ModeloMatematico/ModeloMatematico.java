package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.dao.AlocacaoDao;
import org.desafio.ModeloMatematico.restricoes.*;
import org.desafio.ModeloMatematico.utils.PrintModeloMatematico;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.FuncaoObjetivo;

import java.util.ArrayList;

public class ModeloMatematico {
    MPSolver solver;
    MPSolver.ResultStatus resultStatus;
    MPObjective objective;

    VoluntarioDao voluntarios;
    TurmaDao turmas;
    public AlocacaoDao alocacoes;

    public String exportModelAsLpFormat() {
        return solver.exportModelAsLpFormat();
    }

    public boolean resultadoEhFactivel() {
        return (this.resultStatus == MPSolver.ResultStatus.OPTIMAL);
    }

    public double getSolutionValue() {
        return this.objective.value();
    }

    public long getTempoDeExecucao() {
        return this.solver.wallTime();
    }

    private void construirVariaveis() {
        this.alocacoes = new AlocacaoDao(this.solver, this.voluntarios, this.turmas);
    }

    private void construirRestricoes() {
        for (Turma turma : turmas.getAllTurmas()) {
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma = alocacoes.getPossiveisAlocacoesDeUmaTurma(turma);
            if (alocacoesNaTurma.isEmpty()) {
                continue;
            }

            new QuantidadeDeVoluntariosEmTurma(solver, turma, alocacoesNaTurma);
            new ProporcaoDeGeneroEmTurmas(solver, turma, alocacoesNaTurma);
            new ProporcaoDeExperienciaEmTurmas(solver, turma, alocacoesNaTurma);
            //TODO: proporção entre grau de comprometimento
        }

        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario = alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntario);
            if (alocacoesDoVoluntario.isEmpty()) {
                continue;
            }

            new PermiteApenasUmaAlocacaoDoVoluntario(solver, voluntario, alocacoesDoVoluntario);

            for (Voluntario voluntarioNaEscala : voluntario.getVoluntariosNaMesmaEscala()) {
                ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaMesmaEscala =
                        alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntarioNaEscala);
                if (alocacoesDoVoluntarioNaMesmaEscala.isEmpty()) {
                    continue;
                }

                new AlocacaoDeVoluntariosEmMesmaEscala(
                        solver, voluntario, voluntarioNaEscala, alocacoesDoVoluntario, alocacoesDoVoluntarioNaMesmaEscala);
            }
        }
    }

    public ModeloMatematico(VoluntarioDao voluntarios, TurmaDao turmas) {
        System.out.println("Iniciando modelo!");

        this.voluntarios = voluntarios;
        this.turmas = turmas;

        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("SCIP");

        construirVariaveis();
        construirRestricoes();

        this.objective = new FuncaoObjetivo(this.solver, this.alocacoes).getObjective();
        this.resultStatus = solver.solve();

        new PrintModeloMatematico(this);
        System.out.println("Finalizando modelo!");
    }
}
