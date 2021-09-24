package logic.graph;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintGraph extends GraphImpl<Operation> {

    private Instance instance;

    private Operation source;
    private Operation end;

    private static final int SOURCE_VALUE = Integer.MIN_VALUE;
    private static final int DESTINATION_VALUE = Integer.MAX_VALUE;

    public ConstraintGraph(Instance ins){
        super();
        this.instance = ins;


        this.createGraph();
    }

    private void createGraph(){
        // nodo estado inicial y final
        source = new Operation(SOURCE_VALUE);
        end = new Operation(DESTINATION_VALUE);
        this.addNode(source);
        this.addNode(end);

        // añadir operaciones y relaciones entre trabajos
        this.createOperations();

        // diccionario con las máquinas y los edges relacionando aquellos nodos con máquinas en común
        this.createMatchingOperationsMachines();
    }

    private void createOperations() {
        for (Job j: instance.getJobs()) {
            Operation before = this.source;
            for (Operation current : j.getOperations()) {
                addNode(current);
                addEdge(before, current);
                before = current;
            }
            addEdge(before, end);
        }
    }

    private void createMatchingOperationsMachines(){
        int nMachines = instance.getnMachines();
        Map<Integer, List<Operation>> machineOperations = new HashMap<Integer, List<Operation>>();

        for(Job job : instance.getJobs()) {
            for(Operation op : job.getOperations()) {
                if(!machineOperations.containsKey(op.getMachineNumber())) {
                    machineOperations.put(op.getMachineNumber(), new ArrayList<Operation>());
                }
                machineOperations.get(op.getMachineNumber()).add(op);
            }
        }

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
