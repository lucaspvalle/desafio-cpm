package org.desafio.ModeloMatematico.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AlocacaoDao {
    ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoes = new ArrayList<>();

    //TODO: melhorar funções para reduzir código duplicado
    public ArrayList<AlocacaoDeVoluntarioNaTurma> getPossiveisAlocacoesDeUmaTurma(Turma turma) {
        return (ArrayList<AlocacaoDeVoluntarioNaTurma>)
                this.alocacoes.stream().filter(
                        a -> (a.turma.equals(turma) & a.dominio)
                ).collect(Collectors.toList());
    }

    public ArrayList<AlocacaoDeVoluntarioNaTurma> getPossiveisAlocacoesDeUmVoluntario(Voluntario voluntario) {
        return (ArrayList<AlocacaoDeVoluntarioNaTurma>)
                this.alocacoes.stream().filter(
                        a -> (a.voluntario.equals(voluntario) & a.dominio)
                ).collect(Collectors.toList());
    }

    public ArrayList<AlocacaoDeVoluntarioNaTurma> getAllAlocacoes() {
        return alocacoes;
    }

    public AlocacaoDao(MPSolver solver, VoluntarioDao voluntarios, TurmaDao turmas) {
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            for (Turma turma : turmas.getAllTurmas()) {
                this.alocacoes.add(new AlocacaoDeVoluntarioNaTurma(solver, voluntario, turma));
            }
        }
    }
}
