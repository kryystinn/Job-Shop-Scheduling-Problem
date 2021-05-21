package logic.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {

    private List<Edge> edges;
    private List<Node> nodes;


    public Graph(){
        edges = new ArrayList<Edge>();
        nodes = new ArrayList<Node>();
    }

    public Node addNode(T node){
        // if (!findNode()) y si no es null ...
        return null;
    }

    public Edge addEdge(Node source, Node destination){
        //check si ninguno son null, y si no existe ya el edge en el grafo
        return new Edge(source, destination);
    }
}
