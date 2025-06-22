package org.desafio.ModeloMatematico.variaveis.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;
import org.desafio.ModeloMatematico.variaveis.Variavel;

import java.util.ArrayList;

public class LiberaPeriodoParaMesmaEscalaDao extends Variavel {
    ArrayList<LiberaPeriodoParaMesmaEscala> escalas = new ArrayList<>();

    public ArrayList<LiberaPeriodoParaMesmaEscala> getPossiveisEscalas() {
        return this.escalas;
    }


    public LiberaPeriodoParaMesmaEscalaDao(MPSolver solver, VoluntarioDao voluntarios) {
        // TODO: poderia verificar se o mesmo cadastro de escala está feito para (voluntário, voluntário_aux)
        //       e (voluntário_aux, voluntário) para evitar a construção de restrições redundantes.
        for (Voluntario voluntario : voluntarios.getAllVoluntarios()) {
            for (Voluntario voluntarioNaMesmaEscala : voluntario.getVoluntariosNaMesmaEscala()) {
                for (PreferenciaDePeriodo periodo : PreferenciaDePeriodo.getPeriodosValidos()) {
                    LiberaPeriodoParaMesmaEscala escala = new LiberaPeriodoParaMesmaEscala(
                            solver, voluntario, voluntarioNaMesmaEscala, periodo
                    );

                    if (escala.dominio) {
                        this.escalas.add(escala);
                    }
                }
            }
        }
    }
}