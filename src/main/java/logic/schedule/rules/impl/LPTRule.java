package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

/**
 * Clase LPTRule que representa la regla de prioridad de Longest Processing Time.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class LPTRule implements Rule {

    /**
     * Método que ejecuta la regla de prioridad. Recibe una lista de operaciones y devuelve aquella que tenga mayor
     * tiempo de ejecución.
     * En caso de que las operaciones tengan la misma prioridad eligirá la primera operación.
     *
     * @param operations lista de operaciones
     * @return {@link Operation}
     */
    public Operation run(List<Operation> operations) {
        // Comprobar que la lista no está vacía
        if(operations.isEmpty())  return null;

        Operation longestProcessTime = operations.get(0);

        for (Operation o :operations) {
            if (longestProcessTime.getProcessingTime() < o.getProcessingTime()){
                longestProcessTime = o;
            }

        }

        return longestProcessTime;
    }
}
