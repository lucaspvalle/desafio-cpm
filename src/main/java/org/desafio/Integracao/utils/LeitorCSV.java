package org.desafio.Integracao.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LeitorCSV {
    public ArrayList<String> readFile(String csvPath) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvPath), "windows-1252"))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }
}
