package org.desafio.modeloDeDados;

public enum PreferenciaDePeriodo {
    PERIODO_INDIFERENTE,
    MANHA,
    TARDE;

    PreferenciaDePeriodo getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Manhã" -> MANHA;
            case "Tarde" -> TARDE;
            default -> PERIODO_INDIFERENTE;
        };
    }
}
