package org.desafio;

import org.desafio.Integracao.ImportacaoDeDados;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.ModeloMatematico;


public class Main {
    public static void main(String[] args) {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        TurmaDao turmas = importador.importarTurmas();
        VoluntarioDao voluntarios = importador.importarVoluntarios(turmas);

        new ModeloMatematico(voluntarios, turmas, true);
    }
}