package org.desafio.Integracao;

import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;

import java.util.ArrayList;


public class ImportacaoDeDados {
    public TurmaDao importarTurmas() {
        ArrayList<String> csvTurmas = ArquivosDeIntegracao.TURMA.readIntegrationFile();
        ArrayList<String> csvProgressaoDeTurmas = ArquivosDeIntegracao.PROGRESSAO.readIntegrationFile();
        ArrayList<String> csvEquivalencias = ArquivosDeIntegracao.EQUIVALENCIAS.readIntegrationFile();

        return new TurmaDao(csvTurmas, csvProgressaoDeTurmas, csvEquivalencias);
    }

    public VoluntarioDao importarVoluntarios(TurmaDao turmas) {
        ArrayList<String> csvVoluntarios = ArquivosDeIntegracao.VOLUNTARIOS.readIntegrationFile();
        ArrayList<String> csvVoluntariosEmEscala = ArquivosDeIntegracao.MESMA_ESCALA.readIntegrationFile();

        return new VoluntarioDao(turmas, csvVoluntarios, csvVoluntariosEmEscala);
    }
}
