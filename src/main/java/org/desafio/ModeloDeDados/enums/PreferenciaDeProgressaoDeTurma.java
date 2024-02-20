package org.desafio.modeloDeDados;

public enum PreferenciaDeProgressaoDeTurma {
    PROGRESSAO_INDIFERENTE,
    ACOMPANHAR,
    PERMANECER;

    PreferenciaDeProgressaoDeTurma getPreferencia(String preferencia) {
        return switch (preferencia) {
            case "Acompanhar meus alunos" -> ACOMPANHAR;
            case "Permanecer no mesmo nível" -> PERMANECER;
            default -> PROGRESSAO_INDIFERENTE;
        };
    }
}
