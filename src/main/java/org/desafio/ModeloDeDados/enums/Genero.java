package org.desafio.ModeloDeDados.enums;

public enum Genero {
    FEMININO,
    MASCULINO;

    public static Genero getGenero(String genero) {
        return switch (genero) {
            case "Feminino" -> FEMININO;
            default -> MASCULINO;
        };
    }
}
