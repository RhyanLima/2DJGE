package com.engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class KeyListener {

    public static KeyListener instance;

    private boolean KeyPressed[] = new boolean[350];

    public static KeyListener get()
    {
        if (KeyListener.instance == null)
        {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods)
    {
        if (action == GLFW_PRESS) 
        {
            get().KeyPressed[key] = true;
        }
        else if (action == GLFW_RELEASE) 
        {
            get().KeyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keycode)
    {
        return get().KeyPressed[keycode];
    }

}
