package org.desafio.ModeloMatematico.variaveis.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.HashMap;
import java.util.Map;

public class AlocacaoDao {
    HashMap<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> alocacoes = new HashMap<>();

    public HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> getAlocacoesDaTurma(Turma turma) {
        return this.alocacoes.get(turma);
    }

    public HashMap<Turma, AlocacaoDeVoluntarioNaTurma> getAlocacoesDoVoluntario(Voluntario voluntario) {
        HashMap<Turma, AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario = new HashMap<>();

        for (Map.Entry<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> entry : alocacoes.entrySet()) {
            Turma turma = entry.getKey();
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> alocacoesDaTurma = entry.getValue();

            // Se existir uma chave com o ID do voluntário, salvamos... caso contrário, ignora
            if (alocacoesDaTurma.containsKey(voluntario)) {
                AlocacaoDeVoluntarioNaTurma alocacao = alocacoesDaTurma.get(voluntario);
                alocacoesDoVoluntario.put(turma, alocacao);
            }
        }

        return alocacoesDoVoluntario;
    }

    public HashMap<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> getAlocacoesSugeridas() {
        HashMap<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> alocacoesFiltradas = new HashMap<>();

        for (Map.Entry<Turma, HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma>> entradaTurma : alocacoes.entrySet()) {
            Turma turma = entradaTurma.getKey();
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> mapaInternoOriginal = entradaTurma.getValue();
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> mapaInternoFiltrado = new HashMap<>();

            for (Map.Entry<Voluntario, AlocacaoDeVoluntarioNaTurma> entradaVoluntario : mapaInternoOriginal.entrySet()) {
                Voluntario voluntario = entradaVoluntario.getKey();
                AlocacaoDeVoluntarioNaTurma alocacao = entradaVoluntario.getValue();

                if (alocacao.getSolution() >= 1) {
                    mapaInternoFiltrado.put(voluntario, alocacao);
                }
            }

            if (!mapaInternoFiltrado.isEmpty()) {
                alocacoesFiltradas.put(turma, mapaInternoFiltrado);
            }
        }

        return alocacoesFiltradas;
    }

    public AlocacaoDao(MPSolver solver, VoluntarioDao voluntarios, TurmaDao turmas) {
        for (Turma turma : turmas.getAllTurmas()) {
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> alocacoesDaTurma = new HashMap<>();

            for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
                AlocacaoDeVoluntarioNaTurma alocacao = new AlocacaoDeVoluntarioNaTurma(solver, voluntario, turma);

                if (alocacao.dominio) {
                    alocacoesDaTurma.put(voluntario, alocacao);
                }
            }

            if (!alocacoesDaTurma.isEmpty()) {
                this.alocacoes.put(turma, alocacoesDaTurma);
            }
        }
    }
}
