package org.desafio.ModeloDeDados.dao;

import org.desafio.ModeloDeDados.Voluntario;

import java.util.ArrayList;
import java.util.List;

public class VoluntarioDao {
    List<Voluntario> voluntarios = new ArrayList<>();

    public void addVoluntario(Voluntario voluntario) {
        this.voluntarios.add(voluntario);
    }

    public Voluntario getVoluntarioPorId(Integer id) {
        return this.voluntarios.stream().filter(v -> id.equals(v.getId())).findFirst().orElseThrow(
                () -> new RuntimeException("Voluntário não cadastrado: " + id));
    }

    public Voluntario getVoluntarioPorId(String id) {
        return getVoluntarioPorId(Integer.parseInt(id));
    }

    // TODO: melhorar isso daqui...
    public void updateEscalaDeVoluntario(Voluntario voluntarioAtualizado) {
        this.voluntarios.stream().filter(
                v -> voluntarioAtualizado.getId().equals(v.getId())
        ).findFirst().get().setVoluntariosNaMesmaEscala(voluntarioAtualizado.getVoluntariosNaMesmaEscala());
    }

    public List<Voluntario> getAllVoluntarios() {
        return voluntarios;
    }
}
