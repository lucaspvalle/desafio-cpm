package org.desafio.ModeloMatematico.dao;

import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.Variaveis;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlocacaoDao {
    ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoes = new ArrayList<>();

    private ArrayList<AlocacaoDeVoluntarioNaTurma> filtrarAlocacoes(Predicate<AlocacaoDeVoluntarioNaTurma> predicate) {
        return (ArrayList<AlocacaoDeVoluntarioNaTurma>) this.alocacoes.stream().filter(predicate).collect(Collectors.toList());
    }

    public ArrayList<AlocacaoDeVoluntarioNaTurma> getPossiveisAlocacoesDeUmaTurma(Turma turmaFiltrada) {
        return filtrarAlocacoes(alocacao -> alocacao.turma.equals(turmaFiltrada));
    }

    public ArrayList<AlocacaoDeVoluntarioNaTurma> getPossiveisAlocacoesDeUmVoluntario(Voluntario voluntarioFiltrado) {
        return filtrarAlocacoes(alocacao -> alocacao.voluntario.equals(voluntarioFiltrado));
    }

    public ArrayList<AlocacaoDeVoluntarioNaTurma> getAlocacoes() {
        return this.alocacoes;
    }

    public AlocacaoDao(Variaveis variaveis, VoluntarioDao voluntarios, TurmaDao turmas) {
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            for (Turma turma : turmas.getAllTurmas()) {
                AlocacaoDeVoluntarioNaTurma alocacao = new AlocacaoDeVoluntarioNaTurma(variaveis, voluntario, turma);

                if (alocacao.dominio) {
                    this.alocacoes.add(alocacao);
                }
            }
        }
    }
}
