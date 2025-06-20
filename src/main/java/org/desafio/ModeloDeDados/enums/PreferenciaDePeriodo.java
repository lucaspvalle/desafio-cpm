package org.desafio.ModeloDeDados.enums;

public enum PreferenciaDePeriodo {
    PERIODO_INDIFERENTE,
    MANHA,
    TARDE;

    public static PreferenciaDePeriodo getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Manhã" -> MANHA;
            case "Tarde" -> TARDE;
            default -> PERIODO_INDIFERENTE;
        };
    }

    public static PreferenciaDePeriodo[] getPeriodosValidos() {
        return new PreferenciaDePeriodo[]{MANHA, TARDE};
    }
}
