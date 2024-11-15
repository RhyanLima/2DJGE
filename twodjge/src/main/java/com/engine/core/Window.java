package com.engine.core;

import org.lwjgl.Version;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    public Window() 
    {
        this.width = 1920;
        this.height = 1080;
        this.title = "game";
    }

    // Garante que apenas uma instancia seja Criada
    public static Window get()
    {
        if(Window.window == null)
        {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run()
    {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init()
    {
        // Configurar função callback de erro
        GLFWErrorCallback.createPrint(System.err).set();

        // Inicialização do GLFW
        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to Initialize GLFW");
        }

        // Configuração do GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Craindo a Janela
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL)
        {
            throw new IllegalStateException("Falied to create the GLFW window.");
        }

        // Criando o OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Habilitando v-sync
        glfwSwapInterval(1);

        // Deixando a janela visivel
        glfwShowWindow(glfwWindow);

        // Esta linha é crítica para a interoperação do LWJGL com o GLFW
        // Contexto OpenGL ou qualquer contexto e gerenciado externamente.
        // LWJGL detecta o contexto atual na thread atual,
        // cria a instância GLCapability e faz o OpenGL
        // ligações disponíveis para uso.
        GL.createCapabilities();
    }


    public void loop()
    {
        while (!glfwWindowShouldClose(glfwWindow))
        {
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
    

}
