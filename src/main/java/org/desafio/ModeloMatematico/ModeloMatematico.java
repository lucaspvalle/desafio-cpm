package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.restricoes.*;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;
import org.desafio.ModeloMatematico.variaveis.dao.AlocacaoDao;
import org.desafio.ModeloMatematico.variaveis.dao.LiberaPeriodoParaMesmaEscalaDao;
import org.desafio.ModeloMatematico.variaveis.dao.VariaveisDeTurmaDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ModeloMatematico {
    MPSolver solver;

    public AlocacaoDao alocacoes;
    public VariaveisDeTurmaDao variaveisDeTurma;
    public LiberaPeriodoParaMesmaEscalaDao periodosLiberadosParaEscala;

    public void createVariables(VoluntarioDao voluntarios, TurmaDao turmas) {
        this.alocacoes = new AlocacaoDao(solver, voluntarios, turmas);
        this.variaveisDeTurma = new VariaveisDeTurmaDao(solver, turmas);
        this.periodosLiberadosParaEscala = new LiberaPeriodoParaMesmaEscalaDao(solver, voluntarios);
    }

    public void createConstraints(VoluntarioDao voluntarios, TurmaDao turmas) {
        for (Turma turma : turmas.getAllTurmas()) {
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma =
                    this.alocacoes.getAlocacoesDaTurma(turma);
            if (alocacoesNaTurma.isEmpty()) {
                continue;
            }

            new QuantidadeDeVoluntariosEmTurma(
                    solver, turma, alocacoesNaTurma,
                    this.variaveisDeTurma.getFolgaMaximoVoluntarios(turma),
                    this.variaveisDeTurma.getFolgaMinimoVoluntarios(turma)
                    );
            new ProporcaoDeGeneroEmTurmas(
                    solver, turma, alocacoesNaTurma,
                    this.variaveisDeTurma.getFolgaHomens(turma), this.variaveisDeTurma.getFolgaMulheres(turma)
            );
            new ProporcaoDeExperienciaEmTurmas(
                    solver, turma, alocacoesNaTurma,
                    this.variaveisDeTurma.getFolgaCalouros(turma), this.variaveisDeTurma.getFolgaVeteranos(turma)
            );
            // TODO: grau de comprometimento
        }

        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            HashMap<Turma, AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario =
                    this.alocacoes.getAlocacoesDoVoluntario(voluntario);
            if (alocacoesDoVoluntario.isEmpty()) {
                continue;
            }
            new PermiteApenasUmaAlocacaoDoVoluntario(solver, voluntario, alocacoesDoVoluntario);

            HashMap<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>> mesmaEscala =
                    this.periodosLiberadosParaEscala.getMesmaEscalaDoVoluntario(voluntario);

            for (Map.Entry<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>> entradaMesmaEscala :
                    mesmaEscala.entrySet()) {
                Voluntario voluntarioNaEscala = entradaMesmaEscala.getKey();
                HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala> periodosLiberados =
                        entradaMesmaEscala.getValue();

                HashMap<Turma, AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaEscala =
                        this.alocacoes.getAlocacoesDoVoluntario(voluntarioNaEscala);

                new AlocacaoDeVoluntariosEmMesmaEscala(
                    solver, voluntario, voluntarioNaEscala,
                    alocacoesDoVoluntario, alocacoesDoVoluntarioNaEscala, periodosLiberados);

                new PermiteApenasUmaMesmaEscala(solver, voluntario, voluntarioNaEscala, periodosLiberados);
            }
        }
    }

    public void exportModelAsLpFormat() {
        Path path = Paths.get("lpModel.txt");

        try {
            Files.write(path, solver.exportModelAsLpFormat().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printStatistics(MPSolver.ResultStatus resultStatus) {
        System.out.println();
        System.out.println("Model Statistics");
        System.out.println("Result Status: " + resultStatus);

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Objective Function: " + solver.objective().value() + " (" + solver.wallTime() + " ms)");
        }
    }

    private void printSolution() {
        System.out.println();

        String formatter = "%-15s %-15s %-15s %-15s %-15s%n";
        System.out.printf(formatter, "Voluntário", "Gênero", "Turma", "Período", "Faixa Etária");
        System.out.printf(formatter, "----------", "------", "-----", "-------", "-------------");

        int contagem = 0;
        HashMap<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> alocacoesSugeridas =
                this.alocacoes.getAlocacoesSugeridas();
        for (Map.Entry<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> entradaTurma :
                alocacoesSugeridas.entrySet()) {
            for (AlocacaoDeVoluntarioNaTurma alocacao : entradaTurma.getValue().values()) {
                System.out.printf(
                        formatter,
                        alocacao.voluntario.getId(),
                        alocacao.voluntario.getGenero(),
                        alocacao.turma.getNome(),
                        alocacao.turma.getPeriodo(),
                        alocacao.turma.getFaixaEtaria()
                );
                contagem += 1;
            }
        }
        System.out.println(contagem + " voluntários foram alocados.");
    }

    public ModeloMatematico(VoluntarioDao voluntarios, TurmaDao turmas, boolean debug) {
        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("SCIP");

        createVariables(voluntarios, turmas);
        createConstraints(voluntarios, turmas);

        solver.objective().setMaximization();

        MPSolver.ResultStatus resultStatus = solver.solve();

        printStatistics(resultStatus);
        printSolution();

        if (debug) {
            exportModelAsLpFormat();
        }
    }
}
