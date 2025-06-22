package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;

public class LiberaPeriodoParaMesmaEscala extends Variavel {
    public Voluntario voluntario;
    public Voluntario voluntarioNaMesmaEscala;
    public PreferenciaDePeriodo periodo;

    public boolean dominio;
    public Double peso;

    private boolean voluntarioPodeSerAlocadoNoPeriodo(Voluntario vol) {
        return (vol.getPeriodo() == PreferenciaDePeriodo.PERIODO_INDIFERENTE)
                | (vol.getPeriodo() == periodo);
    }

    public LiberaPeriodoParaMesmaEscala(
            MPSolver solver,
            Voluntario voluntario,
            Voluntario voluntarioNaMesmaEscala,
            PreferenciaDePeriodo periodo
    ) {
        this.voluntario = voluntario;
        this.voluntarioNaMesmaEscala = voluntarioNaMesmaEscala;
        this.periodo = periodo;

        this.dominio = (
                voluntarioPodeSerAlocadoNoPeriodo(voluntario)
                        | voluntarioPodeSerAlocadoNoPeriodo(voluntarioNaMesmaEscala));

        this.peso = 0.0;
        if (this.dominio) {
            this.variavel = makeBoolVar(solver, toString(), this.peso);
        }
    }

    @Override
    public String toString() {
        return "v_LiberaPeriodoParaMesmaEscala{"
                + this.voluntario
                + ", " + this.voluntarioNaMesmaEscala
                + ", " + this.periodo
                + "}";
    }
}