package logic.graph;

/**
 * Clase Edge que representa una arista de un grafo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class Edge {

    private Node source;
    private Node destination;
    private int weight;

    /**
     * Constructor de la clase {@link Edge}.
     *
     * @param src el nodo origen
     * @param dst el nodo destino
     */
    public Edge(Node src, Node dst){
        this.source = src;
        this.destination = dst;
    }

}
