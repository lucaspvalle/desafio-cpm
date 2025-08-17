package org.desafio.ModeloMatematico.variaveis.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;
import org.desafio.ModeloMatematico.variaveis.Variavel;

import java.util.HashMap;

public class LiberaPeriodoParaMesmaEscalaDao extends Variavel {
    HashMap<Voluntario, HashMap<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>>> escalas =
            new HashMap<>();

    public HashMap<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>> getMesmaEscalaDoVoluntario(
            Voluntario voluntario) {
        if (this.escalas.containsKey(voluntario)) {
            return this.escalas.get(voluntario);
        }
        return new HashMap<>();
    }

    public LiberaPeriodoParaMesmaEscalaDao(MPSolver solver, VoluntarioDao voluntarios) {
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            // Quebra se não possuir voluntário em mesma escala
            if (voluntario.getVoluntariosNaMesmaEscala().isEmpty()) {
                continue;
            }
            HashMap<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>> mesmaEscalaDoVoluntario =
                    new HashMap<>();

            for (Voluntario voluntarioNaMesmaEscala : voluntario.getVoluntariosNaMesmaEscala()) {
                HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala> mesmaEscalaDoVoluntarioAux = new HashMap<>();

                // Verifica se o voluntário na mesma escala já foi referenciado na chave invertida de (vol, vol2)
                // e (vol2, vol)
                if (this.escalas.containsKey(voluntarioNaMesmaEscala)) {
                    HashMap<Voluntario, HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala>> verificacaoDeEscala =
                            this.escalas.get(voluntarioNaMesmaEscala);

                    if (verificacaoDeEscala.containsKey(voluntario)) {
                        continue;
                    }
                }

                for (PreferenciaDePeriodo periodo : PreferenciaDePeriodo.getPeriodosValidos()) {
                    LiberaPeriodoParaMesmaEscala escala = new LiberaPeriodoParaMesmaEscala(
                            solver, voluntario, voluntarioNaMesmaEscala, periodo
                    );

                    if (escala.dominio) {
                        mesmaEscalaDoVoluntarioAux.put(periodo, escala);
                    }
                }

                if (!mesmaEscalaDoVoluntarioAux.isEmpty()) {
                    mesmaEscalaDoVoluntario.put(voluntarioNaMesmaEscala, mesmaEscalaDoVoluntarioAux);
                }
            }

            if (!mesmaEscalaDoVoluntario.isEmpty()) {
                this.escalas.put(voluntario, mesmaEscalaDoVoluntario);
            }
        }
    }
}