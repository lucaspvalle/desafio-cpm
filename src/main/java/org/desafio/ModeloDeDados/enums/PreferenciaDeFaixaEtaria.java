package org.desafio.ModeloDeDados.enums;

public enum PreferenciaDeFaixaEtaria {
    FAIXA_INDIFERENTE,
    ADOLESCENTE,
    ADULTO;

    public static PreferenciaDeFaixaEtaria getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Adolescente" -> ADOLESCENTE;
            case "Adulto" -> ADULTO;
            default -> FAIXA_INDIFERENTE;
        };
    }
}
