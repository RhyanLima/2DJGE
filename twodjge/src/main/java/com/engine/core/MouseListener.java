package com.engine.core;

// Importação de constantes do GLFW para detectar eventos de pressionamento e liberação de botões do mouse.
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * A classe MouseListener é responsável por capturar e gerenciar eventos de entrada relacionados ao mouse,
 * como movimento, cliques e rolagem, para serem usados em aplicações como jogos ou interfaces gráficas.
 */
public class MouseListener {

    // Instância singleton da classe
    private static MouseListener instance;

    // Variáveis para armazenar deslocamento de rolagem e posição do mouse
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    
    // Array para rastrear o estado dos botões do mouse
    private boolean mouseButtonPressed[] = new boolean[3]; 
    private boolean isDragging; // Indica se o mouse está sendo arrastado

    /**
     * Construtor privado para implementar o padrão Singleton.
     * Inicializa todas as variáveis com valores padrão.
     */
    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos    = 0.0;
        this.yPos    = 0.0;
        this.lastX   = 0.0;
        this.lastY   = 0.0;
    }

    /**
     * Método para acessar a instância única da classe (Singleton).
     * Se não existir, cria uma nova instância.
     * @return A instância única do MouseListener.
     */
    public static MouseListener get() {
        if(MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    /**
     * Callback para capturar a posição do mouse.
     * @param window Janela associada ao evento.
     * @param xpos Nova posição X do mouse.
     * @param ypos Nova posição Y do mouse.
     */
    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos; // Atualiza a última posição X
        get().lastY = get().yPos; // Atualiza a última posição Y
        get().xPos  = xpos;       // Define a nova posição X
        get().yPos  = ypos;       // Define a nova posição Y
        
        // Determina se o mouse está sendo arrastado
        get().isDragging = get().mouseButtonPressed[0] || 
                           get().mouseButtonPressed[1] || 
                           get().mouseButtonPressed[2];
    }

    /**
     * Callback para capturar eventos de clique dos botões do mouse.
     * @param window Janela associada ao evento.
     * @param button Botão pressionado ou liberado.
     * @param action Ação (pressionar ou liberar).
     * @param mods Modificadores associados ao evento.
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) { 
            // Se o botão está dentro do tamanho do array, marca como pressionado
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            // Marca o botão como liberado e para o arrasto
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;
        }
    }

    /**
     * Callback para capturar eventos de rolagem do mouse.
     * @param window Janela associada ao evento.
     * @param xOffset Deslocamento horizontal da rolagem.
     * @param yOffset Deslocamento vertical da rolagem.
     */
    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    /**
     * Método chamado ao final de cada quadro para redefinir certos estados.
     * Zera o deslocamento da rolagem e atualiza as posições do mouse.
     */
    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX   = get().xPos;
        get().lastY   = get().yPos;
    }

    /**
     * @return Posição atual X do mouse como float.
     */
    public static float getX() {
        return (float)get().xPos;
    }

    /**
     * @return Posição atual Y do mouse como float.
     */
    public static float getY() {
        return (float)get().yPos;
    }

    /**
     * @return Deslocamento em X do último quadro.
     */
    public static float getDx() {
        return (float)(get().lastX - get().xPos);
    }

    /**
     * @return Deslocamento em Y do último quadro.
     */
    public static float getDy() {
        return (float)(get().lastY - get().yPos);
    }

    /**
     * @return Deslocamento horizontal da rolagem.
     */
    public static float getScrollX() {
        return (float)get().scrollX;
    }

    /**
     * @return Deslocamento vertical da rolagem.
     */
    public static float getScrollY() {
        return (float)get().scrollY;
    }

    /**
     * @return Verdadeiro se o mouse está sendo arrastado.
     */
    public static boolean isDragging() {
        return get().isDragging;
    }

    /**
     * Verifica se um botão específico está pressionado.
     * @param button Índice do botão do mouse.
     * @return Verdadeiro se o botão está pressionado, falso caso contrário.
     */
    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }
}
