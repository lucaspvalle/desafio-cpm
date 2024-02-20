package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Turma;

import java.util.ArrayList;
import java.util.List;

public class TurmaDao {
    List<Turma> turmas = new ArrayList<>();

    public void addTurma(Turma turma) {
        this.turmas.add(turma);
    }

    public Turma getTurmaPorId(String nome) {
        return this.turmas.stream().filter(t -> t.getNome().equals(nome)).findFirst().orElseThrow(
                () -> new RuntimeException("Turma não cadastrada: " + nome));
    }

    public void updateTurmaSeguinte(Turma turmaAtualizada) {
        this.turmas.stream().filter(
                t -> turmaAtualizada.getNome().equals(t.getNome())
        ).findFirst().get().setTurmaSeguinte(turmaAtualizada.getTurmaSeguinte());
    }

    public List<Turma> getAllTurmas() {
        return turmas;
    }
}
