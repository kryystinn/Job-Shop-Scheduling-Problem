package logic.graph;

public class Edge {

    private Node source;
    private Node destination;
    private int weight;

    public Edge(Node src, Node dst){
        this.source = src;
        this.destination = dst;
    }

}
