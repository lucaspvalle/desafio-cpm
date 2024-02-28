package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.dao.AlocacaoDao;
import org.desafio.ModeloMatematico.restricoes.PermiteApenasUmaAlocacaoDoVoluntario;
import org.desafio.ModeloMatematico.restricoes.QuantidadeDeVoluntariosEmTurma;
import org.desafio.ModeloMatematico.variaveis.FuncaoObjetivo;

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

    private void construirVariaveis() {
        this.alocacoes = new AlocacaoDao(this.solver, this.voluntarios, this.turmas);
    }

    private void construirRestricoes() {
        for (Turma turma : turmas.getAllTurmas()) {
            new QuantidadeDeVoluntariosEmTurma(solver, turma, alocacoes.getPossiveisAlocacoesDeUmaTurma(turma));
        }

        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            new PermiteApenasUmaAlocacaoDoVoluntario(solver, voluntario, alocacoes.getPossiveisAlocacoesDeUmVoluntario(voluntario));
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

        System.out.println("Finalizando modelo!");
    }
}
