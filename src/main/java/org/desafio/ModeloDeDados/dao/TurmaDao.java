package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Turma;

import java.util.ArrayList;
import java.util.Optional;

public class TurmaDao {
    ArrayList<Turma> turmas = new ArrayList<>();

    public Turma getTurmaPorId(String nome) {
        return this.turmas.stream().filter(
                t -> t.getNome().equals(nome)
        ).findFirst().orElseThrow(() -> new RuntimeException("Turma sem cadastro: " + nome));
    }

    private void updateTurmaSeguinte(Turma turmaAtual, Turma turmaSeguinte) {
        Optional<Turma> turmaAtualDentroDoDao = this.turmas.stream().filter(
                        t -> turmaAtual.getNome().equals(t.getNome())
                ).findFirst();

        turmaAtualDentroDoDao.ifPresent(turma -> turma.setTurmaSeguinte(turmaSeguinte));
    }

    public ArrayList<Turma> getAllTurmas() {
        return turmas;
    }

    public TurmaDao(
            ArrayList<String> csvTurmas,
            ArrayList<String> csvProgressaoDeTurmas,
            ArrayList<String> csvEquivalencias
    ) {
        for (String line : csvTurmas) {
            String[] dadosDeTurmas = line.split(",");
            String nome = dadosDeTurmas[0];
            String faixaEtaria = dadosDeTurmas[1];
            String nivel = dadosDeTurmas[2];
            String periodo = dadosDeTurmas[3];

            String registroDeEquivalencia = csvEquivalencias.stream().filter(
                    dadosDeEquivalencias -> dadosDeEquivalencias.split(",")[0].equals(nivel)
            ).findFirst().orElse("");

            String equivalente = null;
            if (!registroDeEquivalencia.isEmpty()) {
                equivalente = registroDeEquivalencia.split(",")[1];
            }

            this.turmas.add(new Turma(nome, faixaEtaria, nivel, equivalente, periodo));
        }

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
