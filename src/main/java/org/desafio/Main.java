package org.desafio;

import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.ModeloMatematico;

import org.desafio.integracao.ImportacaoDeDados;


public class Main {
    public static void main(String[] args) {
        //[START import]
        ImportacaoDeDados importador = new ImportacaoDeDados();

        TurmaDao turmas = importador.importarTurmas();
        VoluntarioDao voluntarios = importador.importarVoluntarios(turmas);
        //[END import]

        new ModeloMatematico(voluntarios, turmas);
    }
}