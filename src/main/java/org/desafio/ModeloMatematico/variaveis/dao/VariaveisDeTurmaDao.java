package org.desafio.ModeloMatematico.variaveis.dao;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloMatematico.variaveis.*;

import java.util.HashMap;

public class VariaveisDeTurmaDao {
    HashMap<Turma, FolgaMaisCalourosNaTurma> folgaCalouros = new HashMap<>();
    HashMap<Turma, FolgaMaisVeteranosNaTurma> folgaVeteranos = new HashMap<>();
    HashMap<Turma, FolgaMaisHomensNaTurma> folgaHomens = new HashMap<>();
    HashMap<Turma, FolgaMaisMulheresNaTurma> folgaMulheres = new HashMap<>();
    HashMap<Turma, FolgaMaximoDeVoluntariosNaTurma> folgaMaximoVoluntarios = new HashMap<>();
    HashMap<Turma, FolgaMinimoDeVoluntariosNaTurma> folgaMinimoVoluntarios = new HashMap<>();

    public FolgaMaisCalourosNaTurma getFolgaCalouros(Turma turma) {
        return folgaCalouros.get(turma);
    }

    public FolgaMaisVeteranosNaTurma getFolgaVeteranos(Turma turma) {
        return folgaVeteranos.get(turma);
    }

    public FolgaMaisHomensNaTurma getFolgaHomens(Turma turma) {
        return folgaHomens.get(turma);
    }

    public FolgaMaisMulheresNaTurma getFolgaMulheres(Turma turma) {
        return folgaMulheres.get(turma);
    }

    public FolgaMaximoDeVoluntariosNaTurma getFolgaMaximoVoluntarios(Turma turma) {
        return folgaMaximoVoluntarios.get(turma);
    }

    public FolgaMinimoDeVoluntariosNaTurma getFolgaMinimoVoluntarios(Turma turma) {
        return folgaMinimoVoluntarios.get(turma);
    }

    public VariaveisDeTurmaDao(MPSolver solver, TurmaDao turmas) {
        for (Turma turma : turmas.getAllTurmas()) {
            this.folgaCalouros.put(turma, new FolgaMaisCalourosNaTurma(solver, turma));
            this.folgaVeteranos.put(turma, new FolgaMaisVeteranosNaTurma(solver, turma));

            this.folgaHomens.put(turma, new FolgaMaisHomensNaTurma(solver, turma));
            this.folgaMulheres.put(turma, new FolgaMaisMulheresNaTurma(solver, turma));

            this.folgaMaximoVoluntarios.put(turma, new FolgaMaximoDeVoluntariosNaTurma(solver, turma));
            this.folgaMinimoVoluntarios.put(turma, new FolgaMinimoDeVoluntariosNaTurma(solver, turma));
        }
    }
}
