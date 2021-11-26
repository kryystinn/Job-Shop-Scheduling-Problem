package logic.graph;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.Instance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase ConstraintGraph que representa una estructura de un grafo de restricciones.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class ConstraintGraph extends GraphImpl<Operation> {

    private Instance instance;
    private Operation source;
    private Operation end;

    private static final int SOURCE_VALUE = Integer.MIN_VALUE;
    private static final int DESTINATION_VALUE = Integer.MAX_VALUE;


    /**
     * Constructor de la clase {@link ConstraintGraph}
     *
     * @param ins instancia para crear a partir de ella el grafo de restricciones
     */
    public ConstraintGraph(Instance ins){
        super();
        this.instance = ins;

        this.createGraph();
    }

    /**
     * Crea el grafo de restricciones a partir de las operaciones y los trabajos de la instancia.
     *
     */
    private void createGraph(){
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
     */
    private void createOperations() {
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
     */
    private void createMatchingOperationsMachines() {
        Map<Integer, List<Operation>> machineOperations = new HashMap<Integer, List<Operation>>();

        // Añade al diccionario el número de la máquina junto con una lista de operaciones que han de ejecutarse en
        // ella
        for(Job job : instance.getJobs()) {
            for(Operation op : job.getOperations()) {
                if(!machineOperations.containsKey(op.getMachineNumber())) {
                    machineOperations.put(op.getMachineNumber(), new ArrayList<Operation>());
                }
                machineOperations.get(op.getMachineNumber()).add(op);
            }
        }

        // Añadir aristas (edges) entre operaciones que comparten máquina y son
        for(Job job : instance.getJobs()) {
            for (Operation op : job.getOperations()) {
                for(Operation opMach : machineOperations.get(op.getMachineNumber())) {
                    if (op.getJobId() != opMach.getJobId()) {
                        addEdge(op, opMach);
                    }
                }
            }
        }
    }

}
