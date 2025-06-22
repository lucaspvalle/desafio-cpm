package org.desafio.ModeloMatematico.variaveis.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlocacaoDao {
    ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoes = new ArrayList<>();

    public ArrayList<AlocacaoDeVoluntarioNaTurma> filtrarAlocacoes(Predicate<AlocacaoDeVoluntarioNaTurma> predicate) {
        return (ArrayList<AlocacaoDeVoluntarioNaTurma>) this.alocacoes.stream().filter(predicate).collect(Collectors.toList());
    }

    public AlocacaoDao(MPSolver solver, VoluntarioDao voluntarios, TurmaDao turmas) {
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            for (Turma turma : turmas.getAllTurmas()) {
                AlocacaoDeVoluntarioNaTurma alocacao = new AlocacaoDeVoluntarioNaTurma(solver, voluntario, turma);

                if (alocacao.dominio) {
                    this.alocacoes.add(alocacao);
                }
            }
        }
    }
}
