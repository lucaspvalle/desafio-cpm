package org.desafio.modeloDeDados;

public enum PreferenciaDeFaixaEtaria {
    FAIXA_INDIFERENTE,
    ADOLESCENTE,
    ADULTO;

    PreferenciaDeFaixaEtaria getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Adolescente" -> ADOLESCENTE;
            case "Adulto" -> ADULTO;
            default -> FAIXA_INDIFERENTE;
        };
    }
}
