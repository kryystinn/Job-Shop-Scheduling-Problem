package logic.graph;

import logic.instances.Operation;

import java.util.*;

public class GraphImpl<T> implements Graph<T> {

    private Map<T, List<T>> nodes;


    public GraphImpl(){
        nodes = new HashMap<T, List<T>>();
    }

    public void addNode(T node){
        // if (!findNode()) y si no es null ...
        if (nodes.containsKey(node)) {
            return;
        }

        nodes.put(node, new LinkedList<T>());
    }

    @Override
    public void addEdge(T source, T destination) {
        addEdge(source, destination, false);
    }

    @Override
    public void addEdge(T source, T destination, boolean bidirectional) {
        //check si ninguno son null, y si no existe ya el edge en el grafo
        if(source == null || destination == null) {
            return;
        }

        if(hasEdge(source, destination)) {
            return;
        }

        nodes.get(source).add(destination);
        if(bidirectional) {
            nodes.get(destination).add(source);
        }
    }

    @Override
    public boolean hasEdge(T source, T destination) {
        return nodes.get(source).contains(destination);
    }

    @Override
    public List<T> getOutEdges(T node) {
        return nodes.get(node);

    }


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
