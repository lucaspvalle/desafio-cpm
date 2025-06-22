package org.desafio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Parametros {
    public static final int NUM_MAXIMO_VOLUNTARIOS;
    public static final int NUM_MINIMO_VOLUNTARIOS;

    static {
        int tempMaximo = 8;
        int tempMinimo = 3;

        Properties prop = new Properties();

        try (InputStream input = Parametros.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                prop.load(input);
                tempMaximo = Integer.parseInt(prop.getProperty("num.maximo.voluntarios"));
                tempMinimo = Integer.parseInt(prop.getProperty("num.minimo.voluntarios"));
            }
        } catch (IOException e) {
            System.out.println("Erro durante leitura de arquivo.");
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido para um dos parâmetros no application.properties.");
        } finally {
            NUM_MAXIMO_VOLUNTARIOS = tempMaximo;
            NUM_MINIMO_VOLUNTARIOS = tempMinimo;
        }
    }
}