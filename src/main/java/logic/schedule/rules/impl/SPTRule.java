package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;
import java.util.List;

/**
 * Clase SPTRule que representa la regla de prioridad de Shortest Processing Time.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class SPTRule implements Rule {

    /**
     * Método que ejecuta la regla de prioridad. Recibe una lista de operaciones y devuelve aquella que tenga mayor
     * prioridad, calculada como 1/processingTime.
     * En caso de que las operaciones tengan la misma prioridad eligirá la primera operación.
     *
     * @param operations lista de operaciones
     * @return {@link Operation}
     */
    public Operation run(List<Operation> operations) {
        // Comprobar que la lista no está vacía
        if(operations.isEmpty())  return null;

        Operation shortestProcessTime = operations.get(0);

        for (Operation o :operations) {
            if (shortestProcessTime.getProcessingTime() == 0 || o.getProcessingTime() == 0)
                return shortestProcessTime;

            else if (1/shortestProcessTime.getProcessingTime() < 1/o.getProcessingTime()){

                shortestProcessTime = o;
            }

        }

        return shortestProcessTime;
    }

}
