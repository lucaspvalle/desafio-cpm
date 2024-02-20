package org.desafio;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;

import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import org.desafio.integracao.ImportacaoDeDados;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //[START import]
        ImportacaoDeDados importador = new ImportacaoDeDados();

        TurmaDao turmaDao = importador.importarTurmas();
        VoluntarioDao voluntarioDao = importador.importarVoluntarios(turmaDao);
        //[END import]

        Loader.loadNativeLibraries();

        MPSolver solver = MPSolver.createSolver("GLOP");

        List<AlocacaoDeVoluntarioNaTurma> alocacao = new ArrayList<>();
        for (Voluntario voluntario : voluntarioDao.getAllVoluntarios()) {
            for (Turma turma : turmaDao.getAllTurmas()) {
                alocacao.add(new AlocacaoDeVoluntarioNaTurma(solver, voluntario, turma));
            }
        }
        return;
    }
}