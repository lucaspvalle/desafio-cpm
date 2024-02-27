package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Turma;

import java.util.ArrayList;

public class TurmaDao {
    ArrayList<Turma> turmas = new ArrayList<>();

    public Turma getTurmaPorId(String nome) {
        return this.turmas.stream().filter(t -> t.getNome().equals(nome)).findFirst().orElseThrow(
                () -> new RuntimeException("Turma não cadastrada: " + nome));
    }

    private void updateTurmaSeguinte(Turma turmaAtual, Turma turmaSeguinte) {
        this.turmas.stream().filter(
                t -> turmaAtual.getNome().equals(t.getNome())
        ).findFirst().get().setTurmaSeguinte(turmaSeguinte);
    }

    public ArrayList<Turma> getAllTurmas() {
        return turmas;
    }

    public TurmaDao(ArrayList<String> csvTurmas, ArrayList<String> csvProgressaoDeTurmas) {
        // primeiro, cadastra todas as turmas
        for (String line : csvTurmas) {
            Turma turma = new Turma(line.split(","));
            this.turmas.add(turma);
        }

        //em seguida, adiciona as progressões para as turmas cadastradas
        for (String line : csvProgressaoDeTurmas) {
            String[] linhasSeparadas = line.split(",");

            try {
                Turma turmaAtual = getTurmaPorId(linhasSeparadas[0]);
                Turma turmaSeguinte = getTurmaPorId(linhasSeparadas[1]);

                updateTurmaSeguinte(turmaAtual, turmaSeguinte);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
