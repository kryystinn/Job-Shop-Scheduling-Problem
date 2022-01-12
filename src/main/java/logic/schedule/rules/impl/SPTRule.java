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
            else {
                double prior1 = 1.0 / Double.valueOf(shortestProcessTime.getProcessingTime());
                double prior2 = 1.0 / Double.valueOf(o.getProcessingTime());
                if (prior1 < prior2){
                    shortestProcessTime = o;
                }

            }
        }

        return shortestProcessTime;
    }

}
