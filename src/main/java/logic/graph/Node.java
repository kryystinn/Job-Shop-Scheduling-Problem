package logic.graph;

import java.util.HashSet;

/**
 * Clase Node que representa un nodo de un grafo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class Node<T> {

    private T key;

    /**
     * Constructor de la clase {@link Node}.
     *
     * @param key nodo o vértice a crear
     */
    public Node(T key){
        this.key = key;
    }

}
