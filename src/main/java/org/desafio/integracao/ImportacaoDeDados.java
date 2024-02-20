package org.desafio.integracao;

import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ImportacaoDeDados {

    private String getResourceFilePath(String nome) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(nome)).getPath();
    }

    private List<String> importarGenerico(String csvFile) {
        List<String> lines = new ArrayList<>();
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

    private String[] lerLinha(String line, String delimiter) {
        return line.split(delimiter);
    }

    private TurmaDao importarCsvDeCadastroDeTurmas() {
        List<String> csvTurmas = importarGenerico("data/tb_tur_cadastro.csv");
        TurmaDao turmaDao = new TurmaDao();

        for (String line : csvTurmas) {
            turmaDao.addTurma(new Turma(lerLinha(line, ",")));
        }

        return turmaDao;
    }

    private TurmaDao importarCsvDeProgressaoDeTurmas(TurmaDao turmaDao) {
        List<String> csvProgressaoDeTurmas = importarGenerico("data/tb_tur_progressao.csv");

        for (String line : csvProgressaoDeTurmas) {
            String[] linhasSeparadas = lerLinha(line, ",");

            try {
                Turma turmaSeguinte = turmaDao.getTurmaPorId(linhasSeparadas[0]);
                turmaDao.updateTurmaSeguinte(
                        turmaDao.getTurmaPorId(linhasSeparadas[1]).setTurmaSeguinte(turmaSeguinte)
                );

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        return turmaDao;
    }

    private VoluntarioDao importarCsvDeCadastroDeVoluntarios(TurmaDao turmaDao) {
        List<String> csvVoluntarios = importarGenerico("data/tb_vol_cadastro_editado.csv");
        VoluntarioDao voluntarioDao = new VoluntarioDao();

        for (String line : csvVoluntarios) {
            voluntarioDao.addVoluntario(new Voluntario(lerLinha(line, ";"), turmaDao));
        }

        return voluntarioDao;
    }

    private VoluntarioDao importarCsvDeVoluntariosEmMesmaEscala(VoluntarioDao voluntarioDao) {
        List<String> csvVoluntariosEmEscala = importarGenerico("data/tb_vol_mesmaescala.csv");

        for (String line : csvVoluntariosEmEscala) {
            String[] linhasSeparadas = lerLinha(line, ",");

            try {
                Voluntario voluntarioNaEscala = voluntarioDao.getVoluntarioPorId(linhasSeparadas[0]);
                voluntarioDao.updateEscalaDeVoluntario(
                        voluntarioDao.getVoluntarioPorId(linhasSeparadas[1]).addVoluntariosNaMesmaEscala(voluntarioNaEscala)
                );
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        return voluntarioDao;
    }

    public TurmaDao importarTurmas() {
        return importarCsvDeProgressaoDeTurmas(importarCsvDeCadastroDeTurmas());
    }

    public VoluntarioDao importarVoluntarios(TurmaDao turmaDao) {
        return importarCsvDeVoluntariosEmMesmaEscala(importarCsvDeCadastroDeVoluntarios(turmaDao));
    }
}
