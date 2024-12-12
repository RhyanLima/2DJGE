package com.engine;

// Importa a classe base Scene, que fornece a estrutura básica para uma cena no jogo.
import com.engine.core.Scene;

/**
 * A classe LevelScene representa uma cena de jogo onde os jogadores interagem com os níveis.
 * Esta classe herda a funcionalidade básica da classe Scene e implementa comportamentos específicos para esta cena.
 */
public class LevelScene extends Scene {
    
    /**
     * Construtor da classe LevelScene.
     * Exibe uma mensagem no console ao instanciar essa cena, indicando que o jogo está agora na cena de nível.
     */
    public LevelScene() {
        System.out.println("Inside level scene");
    }

    /**
     * Método chamado para atualizar o estado da cena.
     * Este método é executado continuamente no loop principal do jogo.
     * Por enquanto, ele não contém lógica adicional, mas pode ser expandido no futuro para implementar a funcionalidade da cena.
     * 
     * @param deltaTime O tempo decorrido desde a última atualização, usado para sincronização.
     */
    @Override
    public void update(float deltaTime) {
        // Este método está vazio, mas é onde a lógica de atualização para a cena de nível seria implementada.
    }
}
