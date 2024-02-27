package org.desafio.integracao;

import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ImportacaoDeDados {

    private String getResourceFilePath(String nome) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(nome)).getPath();
    }

    private ArrayList<String> importarGenerico(String csvFile) {
        ArrayList<String> lines = new ArrayList<>();
        csvFile = getResourceFilePath(csvFile);

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean cabecalho = true;

            while ((line = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }

                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public TurmaDao importarTurmas() {
        ArrayList<String> csvTurmas = importarGenerico("data/tb_tur_cadastro.csv");
        ArrayList<String> csvProgressaoDeTurmas = importarGenerico("data/tb_tur_progressao.csv");

        return new TurmaDao(csvTurmas, csvProgressaoDeTurmas);
    }

    public VoluntarioDao importarVoluntarios(TurmaDao turmas) {
        ArrayList<String> csvVoluntarios = importarGenerico("data/tb_vol_cadastro_editado.csv");
        ArrayList<String> csvVoluntariosEmEscala = importarGenerico("data/tb_vol_mesmaescala.csv");

        return new VoluntarioDao(turmas, csvVoluntarios, csvVoluntariosEmEscala);
    }
}
