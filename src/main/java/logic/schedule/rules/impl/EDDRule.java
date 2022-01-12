package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

/**
 * Clase EDDRule que representa la regla de prioridad de Earliest Due Date.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class EDDRule implements Rule {

    private List<Job> jobs;
    private static final int HIGH_VALUE = Integer.MAX_VALUE;


    /**
     * Constructor de la cllase {@link EDDRule}
     *
     * @param instance instancia del problema.
     */
     public EDDRule(Instance instance){
        this.jobs = instance.getJobs();
    }

    /**
     * Método que ejecuta la regla de prioridad. Recibe una lista de operaciones y devuelve aquella que tenga mayor
     * prioridad, calculada como 1/dueDate.
     * En caso de que las operaciones tengan la misma prioridad eligirá la operación que tenga menor tiempo de
     * ejecución.
     *
     * @param operations lista de operaciones
     * @return {@link Operation}
     */
    public Operation run(List<Operation> operations){
        // Comprobar que la lista no está vacía
        if(operations.isEmpty())  return null;

        int opJobHasEarliestDueDate = HIGH_VALUE;
        Operation opToSchedule = operations.get(0);

        for (Operation op: operations) {
            for (Job j: jobs) {
                if (op.getJobId() == j.getJobId()) {

                    if (1/j.getDueDate() > 1/opJobHasEarliestDueDate){
                        opJobHasEarliestDueDate = j.getDueDate();
                        opToSchedule = op;

                    }

                    else if (1/j.getDueDate() == 1/opJobHasEarliestDueDate){
                        if (op.getProcessingTime() < opToSchedule.getProcessingTime())
                            opToSchedule = op;
                    }
                }
            }
        }

        return opToSchedule;
    }


}
