package org.desafio.ModeloMatematico.utils;

import org.desafio.ModeloMatematico.ModeloMatematico;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PrintModeloMatematico {
    ModeloMatematico modelo;

    private void imprimirModelo() {
        Path path = Paths.get("lpModel.txt");

        String lpModel = this.modelo.exportModelAsLpFormat();
        try {
            Files.write(path, lpModel.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void imprimirStatusDoModelo() {
        if (this.modelo.resultadoEhFactivel()) {
            System.out.println("Valor da função objetivo: " + this.modelo.getSolutionValue());
        } else {
            System.err.println("Não há solução ótima!");
        }

        System.out.println("Problema resolvido em " + this.modelo.getTempoDeExecucao() + " [ms]");
    }

    private void salvarSolucaoEmCsv() {
        try {
            File file = new File("solucao.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Voluntário;Turma;Alocação");
            bw.newLine();

            for (AlocacaoDeVoluntarioNaTurma alocacao : this.modelo.variaveis.alocacoes.getAllAlocacoesComDominio()) {
                bw.write(alocacao.voluntario.getId() + ";" + alocacao.turma.getNome() + ";" + alocacao.getSolucao());
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Problema ao salvar a solução em CSV!");
        }
    }

    public PrintModeloMatematico(ModeloMatematico modelo) {
        this.modelo = modelo;

        imprimirModelo();
        imprimirStatusDoModelo();
        salvarSolucaoEmCsv();
    }
}
