package logic.graph;

import logic.exceptions.AlgorithmException;
import logic.instances.taillard.TaillardInstance;

import java.util.*;

/**
 * Interfaz Graph que representa la implementación de un grafo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class GraphImpl<T> implements Graph<T> {

    private Map<T, List<T>> nodes;


    /**
     * Constructor de la clase {@link GraphImpl}
     *
     */
    public GraphImpl(){
        nodes = new HashMap<T, List<T>>();
    }

    /**
     * Añade un nuevo nodo o vértice al grafo.
     *
     * @param node nodo a añadir
     */
    public void addNode(T node) throws AlgorithmException {
        if (nodes.containsKey(node)  || node == null) {
            throw new AlgorithmException("Error due to the addition of node to the graph.");
        }

        nodes.put(node, new LinkedList<T>());
    }

    /**
     * Añade una arista desde un nodo hacia otro. Crea, por tanto, una unión dirigida desde un nodo a otro.
     *
     * @param source nodo origen
     * @param destination nodo destino
     * @throws AlgorithmException
     */
    @Override
    public void addEdge(T source, T destination) throws AlgorithmException {
        addEdge(source, destination, false);
    }

    /**
     * Añade una arista entre dos nodos. Puede ser una unión no dirigida en caso de que bidirectional sea true o no
     * dirigida en caso de que sea false.
     *
     * @param source nodo origen
     * @param destination nodo destino
     * @param bidirectional true/false en función de si es una unión no dirigida/dirigida, respectivamente
     * @throws AlgorithmException
     */
    @Override
    public void addEdge(T source, T destination, boolean bidirectional) throws AlgorithmException {
        if (source == null || destination == null || hasEdge(source, destination)) {
            throw new AlgorithmException("Error due to the addition of edge to the graph.");
        }

        nodes.get(source).add(destination);

        if(bidirectional) {
            nodes.get(destination).add(source);
        }
    }

    /**
     * Comprueba si dos nodos están conectados, es decir, si existe una arista (un edge) entre ellos.
     *
     * @param source nodo origen
     * @param destination nodo destino
     * @return true/false en función de si están conectados o no
     */
    @Override
    public boolean hasEdge(T source, T destination) {
        return nodes.get(source).contains(destination);
    }

    /**
     * Devuelve una lista con los nodos a los que apunta mediante aristas (edges) el nodo que se pasa por parámetro.
     * Es decir, dada una arista e = (x,y), siendo x el nodo emisor (el que se introduce por parámetro) e y el nodo
     * receptor, la lista contendría todos los nodos receptores que haya en el grafo.
     *
     * @param node nodo origen desde el que encontrar nodos receptores
     * @return lista con los nodos receptores del grafo
     */
    @Override
    public List<T> getOutEdges(T node) {
        return nodes.get(node);

    }

    /**
     * Devuelve una lista con los nodos que apuntan mediante aristas (edges) al nodo que se pasa por parámetro.
     * Es decir, dada una arista e = (x,y), siendo x el nodo emisor e y el nodo receptor (que se pasa por parámetro),
     * la lista contendría todos los nodos emisores que haya en el grafo.
     *
     * @param node nodo destino desde el que encontrar nodos emisores
     * @return lista con todos los nodos emisores del grafo
     */
    @Override
    public List<T> getInEdges(T node) {
    List<T> incomingEdges = new ArrayList<T>();
        for (T n: nodes.keySet()) {
            if (hasEdge(n, node)){
                incomingEdges.add(n);
            }
        }

        return incomingEdges;
    }


}
