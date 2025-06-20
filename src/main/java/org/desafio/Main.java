package org.desafio;

import org.desafio.Integracao.ImportacaoDeDados;
import org.desafio.ModeloMatematico.ModeloMatematico;


public class Main {
    public static void main(String[] args) {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        new ModeloMatematico(importador.voluntarios, importador.turmas);
    }
}