package com.engine.core;

// Importa a anotação @NoArgsConstructor da biblioteca Lombok para gerar automaticamente um construtor sem argumentos.
import lombok.NoArgsConstructor;

/**
 * A classe abstrata Scene serve como uma estrutura básica para diferentes cenas do jogo.
 * É projetada para ser estendida por classes específicas que implementam cenas específicas, 
 * como menus, níveis ou editores de nível.
 */
@NoArgsConstructor
public abstract class Scene {

    /**
     * Método abstrato que deve ser implementado pelas subclasses.
     * Este método é chamado continuamente no loop principal do jogo para atualizar o estado da cena.
     *
     * @param deltaTime O tempo decorrido desde a última atualização, usado para sincronizar eventos e animações.
     */
    public abstract void update(float deltaTime);
}
