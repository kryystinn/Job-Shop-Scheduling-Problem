package logic.graph;

import java.util.List;

public interface Graph<T> {

    void addNode(T node);

    void addEdge(T source, T destination);
    void addEdge(T source, T destination, boolean bidirectional);

    boolean hasEdge(T source, T destination);

    List<T> getOutEdges(T node);

    List<T> getInEdges(T node);
}
