package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Turma;

import java.util.ArrayList;

public class TurmaDao {
    ArrayList<Turma> turmas = new ArrayList<>();

    public Turma getTurmaPorId(String nome) {
        return this.turmas.stream().filter(
                t -> t.getNome().equals(nome)
        ).findFirst().orElseThrow(() -> new RuntimeException("Turma sem cadastro: " + nome));
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
            String[] dadosDeProgressao = line.split(",");
            String turmaAtual = dadosDeProgressao[0];

            try {
                Turma turmaSeguinte = getTurmaPorId(dadosDeProgressao[1]);
                this.turmas.stream().filter(
                        t -> turmaAtual.equals(t.getNome())
                ).findFirst().ifPresent(turmaAtualDentroDoDao -> turmaAtualDentroDoDao.setTurmaSeguinte(turmaSeguinte));
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
