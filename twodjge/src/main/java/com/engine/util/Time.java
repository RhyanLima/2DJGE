package com.engine.util;

/**
 * Classe utilitária para gerenciamento de tempo no jogo.
 * Calcula o tempo decorrido desde o início da execução da aplicação.
 */
public class Time {

    // Marca o momento em que a aplicação foi iniciada, em nanosegundos.
    public static float timeStarted = System.nanoTime();

    /**
     * Método para obter o tempo atual decorrido desde o início da aplicação.
     * 
     * @return Tempo decorrido em segundos como um valor de ponto flutuante.
     */
    public static float getTime() {
        // Calcula a diferença entre o tempo atual e o tempo de início,
        // convertendo de nanosegundos para segundos.
        return (float)((System.nanoTime() - timeStarted) * 1E-9);
    }

}
