package org.desafio.importacao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.desafio.modeloDeDados.EquivalenciaDeNivel;
import org.desafio.modeloDeDados.ProgressaoDeTurma;
import org.desafio.modeloDeDados.Turma;

public class ImportacaoDeDados {

    private String getResourceFilePath(String nome) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(nome)).getPath();
    }

    private <T> List<T> importarGenerico(String csvFile, Class<T> clazz) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Turma> importarTurmas() {
        String csvFile = getResourceFilePath("data/tb_tur_cadastro.csv");

        List<Turma> turmas = importarGenerico(csvFile, Turma.class);
        for (Turma turma : turmas) {
            turma.setEnums();
        }
        return turmas;
    }

    private List<EquivalenciaDeNivel> importarEquivalencias() {
        String csvFile = getResourceFilePath("data/tb_dm_equivalencia.csv");

        List<EquivalenciaDeNivel> equivalencias = importarGenerico(csvFile, EquivalenciaDeNivel.class);
        for (EquivalenciaDeNivel equivalencia : equivalencias) {
            equivalencia.setEnums();
        }
        return equivalencias;
    }

    private List<ProgressaoDeTurma> importarProgressaoDeTurma() {
        String csvFile = getResourceFilePath("data/tb_tur_progressao.csv");

        return importarGenerico(csvFile, ProgressaoDeTurma.class);
    }

    public ImportacaoDeDados() {
        List<EquivalenciaDeNivel> equivalencias = importarEquivalencias();

        List<Turma> turmas = importarTurmas();
        List<ProgressaoDeTurma> progressao = importarProgressaoDeTurma();

        return;
    }

}
