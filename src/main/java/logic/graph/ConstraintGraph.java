package logic.graph;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.taillard.TaillardInstance;

public class ConstraintGraph extends Graph {

    private TaillardInstance instance;
    private Node source;
    private Node end;
    private Graph graph;

    private static final int MIN_VALUE = Integer.MIN_VALUE;
    private static final int MAX_VALUE = Integer.MAX_VALUE;

    public ConstraintGraph(){
        this.graph = new Graph();
        source = new Node(MIN_VALUE);
        end = new Node(MAX_VALUE);
    }

    private void createGraph(){
        for (Job j: instance.getJobs()) {
            Node iteration = source;
            Node current = iteration;
            for (Operation o : j.getOperations()) {
                current = graph.addNode(o);
                graph.addEdge(iteration, current);
                iteration = current;
            }
            graph.addEdge(iteration, end);
        }

        // diccionario con las máquinas y los edges relacionando los nodos con máquinas en común
    }

}
