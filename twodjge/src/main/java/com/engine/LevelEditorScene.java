package com.engine;

// Importações necessárias para eventos de teclado e integração com outras partes do sistema.
import java.awt.event.KeyEvent;

import com.engine.core.KeyListener;
import com.engine.core.Scene;
import com.engine.core.Window;

/**
 * A classe LevelEditorScene representa a cena de edição de níveis no jogo.
 * Herda a funcionalidade básica da classe Scene e adiciona lógica específica para essa cena.
 */
public class LevelEditorScene extends Scene {
    
    // Variável para controlar se a mudança de cena foi iniciada.
    private boolean changingScene = false;

    // Tempo restante antes de mudar para outra cena, em segundos.
    private float timeToChangeScene = 2.0f;

    /**
     * Construtor da classe LevelEditorScene.
     * Exibe uma mensagem no console ao instanciar essa cena.
     */
    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    /**
     * Método chamado para atualizar o estado da cena.
     * Este método é executado continuamente no loop principal do jogo.
     * 
     * @param deltaTime O tempo decorrido desde a última atualização, usado para sincronização.
     */
    @Override
    public void update(float deltaTime) {
        
        // Verifica se a tecla ESPAÇO foi pressionada e a mudança de cena ainda não começou.
        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true; // Marca que a mudança de cena foi iniciada.
        }
        
        // Se a mudança de cena foi iniciada, decrementa o tempo restante e altera as cores da janela.
        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= deltaTime; // Reduz o tempo restante.

            // Reduz os componentes de cor RGB da janela gradualmente.
            Window.get().r -= deltaTime * 5.0f;
            Window.get().g -= deltaTime * 5.0f;
            Window.get().b -= deltaTime * 5.0f;

        } else {
            // Quando o tempo acabar, troca para a próxima cena (LevelScene).
            Window.changeScene(1);
        }
    }
}
