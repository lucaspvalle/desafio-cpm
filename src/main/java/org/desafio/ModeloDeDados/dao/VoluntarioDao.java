package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;

import java.util.ArrayList;

public class VoluntarioDao {
    ArrayList<Voluntario> voluntarios = new ArrayList<>();

    public Voluntario getVoluntarioPorId(Integer id) {
        return this.voluntarios.stream().filter(v -> id.equals(v.getId())).findFirst().orElseThrow(
                () -> new RuntimeException("Vol. sem cadastro: " + id));
    }

    public ArrayList<Voluntario> getAllVoluntarios() {
        return voluntarios;
    }

    public VoluntarioDao(
            TurmaDao turmas,
            ArrayList<String> csvVoluntarios,
            ArrayList<String> csvVoluntariosEmEscala
    ) {
        for (String line : csvVoluntarios) {
            String[] dadosDeVoluntario = line.split(";");

            int id = Integer.parseInt(dadosDeVoluntario[0]);
            String genero = dadosDeVoluntario[1];
            // String funcao = dadosDeVoluntario[2];
            int anoDeInicio = Integer.parseInt(dadosDeVoluntario[3].split(",")[0]);
            Turma turma = turmas.getTurmaPorId(dadosDeVoluntario[4]);
            Double comprometimento = Double.parseDouble(dadosDeVoluntario[5].replace(',', '.'));
            String nivel = dadosDeVoluntario[6];
            String periodo = dadosDeVoluntario[7];
            String progressao = dadosDeVoluntario[8];
            String faixaEtaria = dadosDeVoluntario[9];

            this.voluntarios.add(new Voluntario(
                    id, genero, anoDeInicio, turma, comprometimento, nivel, periodo, progressao, faixaEtaria));
        }

        for (String line : csvVoluntariosEmEscala) {
            String[] dadosDeMesmaEscala = line.split(",");

            try {
                Voluntario voluntario = getVoluntarioPorId(Integer.valueOf(dadosDeMesmaEscala[0]));
                Voluntario voluntarioNaMesmaEscala = getVoluntarioPorId(Integer.valueOf(dadosDeMesmaEscala[1]));
                this.voluntarios.stream().filter(
                        v -> voluntario.getId().equals(v.getId())
                ).findFirst().ifPresent(volDentroDoDao ->
                        volDentroDoDao.setVoluntariosNaMesmaEscala(voluntarioNaMesmaEscala));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}