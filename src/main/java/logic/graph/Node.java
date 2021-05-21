package logic.graph;

import java.util.HashSet;

public class Node<T> {

    private T key;
    private HashSet<Node> outEdges;
    private HashSet<Node> inEdges;

    public Node(T key){
        this.key = key;
    }

}
