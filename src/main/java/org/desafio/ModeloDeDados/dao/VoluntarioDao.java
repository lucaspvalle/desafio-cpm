package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Voluntario;

import java.util.ArrayList;
import java.util.Optional;

public class VoluntarioDao {
    ArrayList<Voluntario> voluntarios = new ArrayList<>();

    public Voluntario getVoluntarioPorId(Integer id) {
        return this.voluntarios.stream().filter(v -> id.equals(v.getId())).findFirst().orElseThrow(
                () -> new RuntimeException("Voluntário não cadastrado: " + id));
    }

    public ArrayList<Voluntario> getAllVoluntarios() {
        return voluntarios;
    }

    private void updateEscalaDeVoluntario(Voluntario voluntario, Voluntario voluntarioNaMesmaEscala) {
        Optional<Voluntario> voluntarioDentroDoDao = this.voluntarios.stream().filter(
                v -> voluntario.getId().equals(v.getId())).findFirst();

        voluntarioDentroDoDao.ifPresent(vol -> vol.setVoluntariosNaMesmaEscala(voluntarioNaMesmaEscala));
    }

    public VoluntarioDao(TurmaDao turmas, ArrayList<String> csvVoluntarios, ArrayList<String> csvVoluntariosEmEscala) {
        // primeiro, cadastra todos os voluntários
        for (String line : csvVoluntarios) {
            Voluntario voluntario = new Voluntario(line.split(";"), turmas);
            this.voluntarios.add(voluntario);
        }

        //atualiza voluntários em mesma escala
        for (String line : csvVoluntariosEmEscala) {
            String[] linhasSeparadas = line.split(",");

            try {
                Voluntario voluntario = getVoluntarioPorId(Integer.valueOf(linhasSeparadas[0]));
                Voluntario voluntarioNaMesmaEscala = getVoluntarioPorId(Integer.valueOf(linhasSeparadas[1]));

                updateEscalaDeVoluntario(voluntario, voluntarioNaMesmaEscala);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
