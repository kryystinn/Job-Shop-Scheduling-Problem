package logic.graph;

import logic.exceptions.AlgorithmException;
import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.Instance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase ConstraintGraph que representa la estructura de un grafo de restricciones.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class ConstraintGraph extends GraphImpl<Operation> {

    private final Instance instance;
    private Operation source;
    private Operation end;

    private static final int SOURCE_VALUE = Integer.MIN_VALUE;
    private static final int DESTINATION_VALUE = Integer.MAX_VALUE;


    /**
     * Constructor de la clase {@link ConstraintGraph}
     *
     * @param ins instancia para crear a partir de ella el grafo de restricciones
     * @throws AlgorithmException si hay fallos al crear el grafo de restricciones
     */
    public ConstraintGraph(Instance ins) throws AlgorithmException {
        super();
        this.instance = ins;

        this.createGraph();
    }

    /**
     * Crea el grafo de restricciones a partir de las operaciones y los trabajos de la instancia.
     *
     * @throws AlgorithmException si hay fallos al crear el grafo de restricciones
     */
    private void createGraph() throws AlgorithmException {
        // Nodo estado inicial y final
        source = new Operation(SOURCE_VALUE);
        end = new Operation(DESTINATION_VALUE);
        this.addNode(source);
        this.addNode(end);

        // Añadir operaciones y relaciones entre operaciones del mismo job (precedencia)
        this.createOperations();

        // Diccionario con las máquinas y las aristas (edges) relacionando aquellos nodos con máquinas en común
        this.createMatchingOperationsMachines();
    }

    /**
     * Crea las operaciones añadiendo entre una y la siguiente una arista (edge) (del mismo job), siendo la primera
     * unión de todos los nodos con el source y la última de todos los nodos el end.
     *
     * @throws AlgorithmException si hay fallos al crear los nodos del grafo de restricciones
     */
    private void createOperations() throws AlgorithmException {
        for (Job j: instance.getJobs()) {
            // Primer nodo de todos: el source
            Operation before = this.source;
            for (Operation current : j.getOperations()) {
                addNode(current);
                addEdge(before, current);
                before = current;
            }
            // Último nodo de todos: el end
            addEdge(before, end);
        }
    }

    /**
     * Crea un diccionario donde guarda las máquinas (el número) junto con una lista de las operaciones (o nodos) que
     * tienen asignada dicha máquina.
     * Seguidamente, recorre todas las operaciones de cada trabajo, añadiendo un arista a todas aquellas operaciones
     * que coincidan en la máquina que tienen asignada para ejecutarse.
     *
     * @throws AlgorithmException si hay fallos al crear las relaciones del grafo de restricciones
     */
    private void createMatchingOperationsMachines() throws AlgorithmException {
        Map<Integer, List<Operation>> machineOperations = new HashMap<>();

        // Añade al diccionario el número de la máquina junto con una lista de operaciones que han de ejecutarse en
        // ella
        for(Job job : instance.getJobs()) {
            for(Operation op : job.getOperations()) {
                if(!machineOperations.containsKey(op.getMachineId())) {
                    machineOperations.put(op.getMachineId(), new ArrayList<>());
                }
                machineOperations.get(op.getMachineId()).add(op);
            }
        }

        // Añadir aristas (edges) entre operaciones que comparten máquina y son
        for(Job job : instance.getJobs()) {
            for (Operation op : job.getOperations()) {
                for(Operation opMach : machineOperations.get(op.getMachineId())) {
                    if (op.getJobId() != opMach.getJobId()) {
                        addEdge(op, opMach);
                    }
                }
            }
        }
    }


}
