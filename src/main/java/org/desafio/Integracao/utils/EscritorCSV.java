package org.desafio.Integracao.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class EscritorCSV {
    public void writeFile(String csvFile, String[] data) {
        try (PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(csvFile), "windows-1252"))) {
            for (String line : data) {
                printWriter.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}