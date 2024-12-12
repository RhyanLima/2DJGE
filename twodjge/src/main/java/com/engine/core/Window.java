package com.engine.core;

// Importações necessárias para o uso do GLFW e OpenGL.
import org.lwjgl.Version;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.engine.LevelEditorScene;
import com.engine.LevelScene;
import com.engine.util.Time;

/**
 * A classe Window gerencia a criação e o funcionamento de uma janela,
 * incluindo inicialização, troca de cenas e o loop principal de renderização.
 * É baseada em GLFW para controle de janelas e eventos e OpenGL para renderização.
 */
public class Window {

    // Largura e altura da janela.
    private int width, height;

    // Título da janela.
    private String title;

    // Identificador do objeto da janela no GLFW.
    private long glfwWindow;

    // Variáveis de cor de fundo da janela (RGBA).
    public float r, g, b, a;

    // Instância única da classe Window (Singleton).
    private static Window window = null;

    // Referência para a cena atual exibida na janela.
    private static Scene currentScene;

    /**
     * Construtor padrão, inicializa a janela com valores predefinidos.
     */
    public Window() {
        this.width = 1920; // Largura padrão.
        this.height = 1080; // Altura padrão.
        this.title = "game"; // Título padrão.
        r = 0; g = 0; b = 0; a = 0; // Cor de fundo padrão (preto).
    }

    /**
     * Troca a cena atual com base em um identificador numérico.
     * @param newScene O identificador da nova cena a ser exibida.
     */
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0: // Cena do editor de nível.
                currentScene = new LevelEditorScene();
                break;
            case 1: // Cena do nível do jogo.
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
        }
    }

    /**
     * Obtém a instância única da classe Window.
     * Implementa o padrão Singleton para garantir uma única instância.
     * @return A instância única da classe Window.
     */
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    /**
     * Inicia a aplicação, incluindo inicialização da janela e loop principal.
     */
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        // Inicializa a janela.
        init();

        // Executa o loop principal.
        loop();

        // Libera recursos do GLFW.
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Inicializa a janela e configura o GLFW.
     */
    public void init() {
        // Define o callback para exibição de erros do GLFW no console.
        GLFWErrorCallback.createPrint(System.err).set();

        // Inicializa o GLFW.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to Initialize GLFW");
        }

        // Configurações padrão da janela.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Janela inicialmente invisível.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Permite redimensionar a janela.
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // Inicia maximizada.

        // Cria a janela com os parâmetros especificados.
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Configura callbacks para eventos do mouse e teclado.
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Define o contexto OpenGL da janela.
        glfwMakeContextCurrent(glfwWindow);

        // Habilita V-Sync (sincronização com a taxa de atualização do monitor).
        glfwSwapInterval(1);

        // Torna a janela visível.
        glfwShowWindow(glfwWindow);

        // Configura o OpenGL.
        GL.createCapabilities();

        // Inicializa a cena padrão.
        Window.changeScene(0);
    }

    /**
     * Loop principal da aplicação.
     * Controla o fluxo contínuo de eventos, lógica e renderização até que a janela seja fechada.
     */
    public void loop() {
        // Controla o tempo para calcular o deltaTime (tempo entre frames).
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float deltaTime = -1.0f;

        // Executa enquanto a janela não for fechada.
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Processa eventos pendentes (teclado, mouse, etc.).
            glfwPollEvents();

            // Define a cor de fundo e limpa o buffer de cor.
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            // Atualiza a cena atual com base no deltaTime.
            if (deltaTime >= 0) {
                currentScene.update(deltaTime);
            }

            // Exemplo de verificação de entrada: verifica se a tecla ESPAÇO está pressionada.
            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space key is pressed!");
            }

            // Troca os buffers para exibir o frame atual.
            glfwSwapBuffers(glfwWindow);

            // Atualiza o tempo para calcular o deltaTime.
            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
