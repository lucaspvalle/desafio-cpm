package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.dao.AlocacaoDao;
import org.desafio.ModeloMatematico.restricoes.AlocacaoDeVoluntariosEmMesmaEscala;
import org.desafio.ModeloMatematico.restricoes.PermiteApenasUmaAlocacaoDoVoluntario;
import org.desafio.ModeloMatematico.restricoes.QuantidadeDeVoluntariosEmTurma;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.FuncaoObjetivo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModeloMatematico {
    MPSolver solver;
    MPSolver.ResultStatus resultStatus;
    MPObjective objective;

    VoluntarioDao voluntarios;
    TurmaDao turmas;
    AlocacaoDao alocacoes;

    private void imprimirModelo() {
        Path path = Paths.get("lpModel.txt");

        String lpModel = this.solver.exportModelAsLpFormat();
        try {
            Files.write(path, lpModel.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void imprimirStatusDoModelo() {
        if (this.resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Valor da função objetivo: " + this.objective.value());
        } else {
            System.err.println("Não há solução ótima!");
        }

        System.out.println("Problema resolvido em " + this.solver.wallTime() + " [ms]");
    }

    private void salvarSolucaoEmCsv() {
        try {
            File file = new File("solucao.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Voluntário;Turma;Alocação");
            bw.newLine();

            for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoes.getAllAlocacoesComDominio()) {
                bw.write(alocacao.voluntario.getId() + ";" + alocacao.turma.getNome() + ";" + (int)alocacao.variavel.solutionValue());
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Problema ao salvar a solução em CSV!");
        }
    }

    private void construirVariaveis() {
        this.alocacoes = new AlocacaoDao(this.solver, this.voluntarios, this.turmas);
    }

    private void construirRestricoes() {
        for (Turma turma : turmas.getAllTurmas()) {
            new QuantidadeDeVoluntariosEmTurma(solver, turma, alocacoes.getPossiveisAlocacoesDeUmaTurma(turma));
        }

        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            new PermiteApenasUmaAlocacaoDoVoluntario(solver, voluntario, alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntario));

            for (Voluntario voluntarioNaEscala : voluntario.getVoluntariosNaMesmaEscala()) {
                for (PreferenciaDePeriodo periodo : PreferenciaDePeriodo.getPeriodosValidos()) {
                    new AlocacaoDeVoluntariosEmMesmaEscala(
                            solver,
                            voluntario,
                            voluntarioNaEscala,
                            periodo,
                            alocacoes.getPossiveisAlocacoesDeUmVoluntarioEmUmPeriodo(voluntario, periodo),
                            alocacoes.getPossiveisAlocacoesDeUmVoluntarioEmUmPeriodo(voluntarioNaEscala, periodo)
                    );
                }
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

        imprimirStatusDoModelo();
        imprimirModelo();
        salvarSolucaoEmCsv();

        System.out.println("Finalizando modelo!");
    }
}
