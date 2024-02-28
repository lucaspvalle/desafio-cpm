package org.desafio;

import org.desafio.ModeloMatematico.ModeloMatematico;
import org.desafio.integracao.ImportacaoDeDados;


public class Main {
    public static void main(String[] args) {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        new ModeloMatematico(importador.voluntarios, importador.turmas);
    }
}