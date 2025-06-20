package org.desafio.ModeloDeDados.enums;

public enum PreferenciaDeFaixaEtaria {
    FAIXA_INDIFERENTE,
    ADOLESCENTE,
    ADULTO;

    public static PreferenciaDeFaixaEtaria getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Adolescentes" -> ADOLESCENTE;
            case "Adulto" -> ADULTO;
            default -> FAIXA_INDIFERENTE;
        };
    }
}
