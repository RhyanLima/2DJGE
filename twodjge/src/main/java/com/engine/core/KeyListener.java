package com.engine.core;

// Importação de constantes do GLFW para detectar eventos de pressionamento e liberação de teclas.
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import lombok.NoArgsConstructor;

/**
 * A classe KeyListener é responsável por capturar e gerenciar eventos relacionados às teclas do teclado,
 * permitindo que outros componentes verifiquem o estado das teclas pressionadas.
 */
@NoArgsConstructor
public class KeyListener {

    // Instância singleton da classe.
    public static KeyListener instance;

    // Array booleano para armazenar o estado (pressionado ou não) de até 350 teclas.
    private boolean KeyPressed[] = new boolean[350];

    /**
     * Método para acessar a instância única da classe (Singleton).
     * Se não existir, cria uma nova instância.
     * @return A instância única do KeyListener.
     */
    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    /**
     * Callback para capturar eventos do teclado.
     * Este método é chamado automaticamente quando uma tecla é pressionada ou liberada.
     * @param window Janela associada ao evento.
     * @param key Código da tecla que gerou o evento.
     * @param scancode Código específico do hardware para a tecla (não utilizado aqui).
     * @param action Ação realizada (pressionar ou liberar).
     * @param mods Modificadores (teclas como Shift, Ctrl, etc., que estavam ativas durante o evento).
     */
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            // Marca a tecla como pressionada no array.
            get().KeyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            // Marca a tecla como liberada no array.
            get().KeyPressed[key] = false;
        }
    }

    /**
     * Verifica se uma tecla específica está atualmente pressionada.
     * @param keycode O código da tecla a ser verificado.
     * @return Verdadeiro se a tecla está pressionada, falso caso contrário.
     */
    public static boolean isKeyPressed(int keycode) {
        return get().KeyPressed[keycode];
    }
}
