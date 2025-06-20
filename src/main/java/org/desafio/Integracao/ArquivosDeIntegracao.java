package org.desafio.Integracao;

import java.util.Objects;

public enum ArquivosDeIntegracao {
    TURMA("tb_tur_cadastro"),
    PROGRESSAO("tb_tur_progressao"),
    EQUIVALENCIAS("tb_dm_equivalencia"),
    VOLUNTARIOS("tb_vol_cadastro_editado"),
    MESMA_ESCALA("tb_vol_mesmaescala");

    private final String arquivo;
    ArquivosDeIntegracao(String arquivo) {
        this.arquivo = arquivo;
    }

    private String getResourceFilePath(String csvFile) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(csvFile)).getPath();
    }

    public String getCaminho() {
        return getResourceFilePath("data/" + this.arquivo + ".csv");
    }
}
