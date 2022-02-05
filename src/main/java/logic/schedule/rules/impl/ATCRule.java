package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Clase ATCRule que representa la regla de prioridad de Apparent Tardiness Cost.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class ATCRule implements Rule {

    private final List<Job> jobs;
    private final double kValue;
    private final Rule auxRule;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Constructor de la clase {@link ATCRule}, con un valor de g por defecto de 0.5 y la EDD de regla auxiliar.
     *
     * @param instance instancia del problema
     */
    public ATCRule(Instance instance) {
        this(instance, 0.5, new EDDRule(instance));
    }

    /**
     * Constructor de la clase {@link ATCRule} con la EDD de regla auxiliar por defecto
     *
     * @param instance instancia del problema
     * @param gValue valor de g
     */
    public ATCRule(Instance instance, double gValue) {
        this.jobs = instance.getJobs();
        this.kValue = gValue;
        this.auxRule = new EDDRule(instance);
    }

    /**
     * Constructor de la clase {@link ATCRule}
     *
     * @param instance instancia del problema
     * @param kValue valor de g
     * @param auxRule regla auxiliar a utilizar
     */
    public ATCRule(Instance instance, double kValue, Rule auxRule) {
        this.jobs = instance.getJobs();
        this.kValue = kValue;
        this.auxRule = auxRule;
    }

    /**
     * Clase anidada {@link MaxOperation} para la gestión de los sets de operación y prioridad.
     *
     */
    static class MaxOperation {
        Operation operation;
        double priority;

        MaxOperation(Operation op, double priority) {
            this.operation = op;
            this.priority = priority;
        }
    }

    /**
     * Método que ejecuta la regla de prioridad. Recibe una lista de operaciones y devuelve aquella que tenga mayor
     * prioridad, calculada según la fórmula de la ATC.
     *
     * @param operations lista de operaciones
     * @return {@link Operation}
     */
    @Override
    public Operation run(List<Operation> operations) {

        // Coger los pesos y las due dates de los jobs a los que pertenecen las operaciones del set B
        MaxOperation best = new MaxOperation(null, 0);
        for (Operation op : operations) {
            for (Job j : jobs) {

                if (op.getJobId() == j.getJobId()) {
                    double weight = j.getWeight();
                    int dueDate = j.getDueDate();

                    double priority = function(op, weight, dueDate);

                    if(priority > best.priority) {
                        best = new MaxOperation(op, priority);
                    }

                    else if (best.operation != null && priority == best.priority) {
                        List<Operation> equalPriorities = new ArrayList<>();
                        equalPriorities.add(op);
                        equalPriorities.add(best.operation);
                        auxRule.run(equalPriorities);
                    }

                }
            }
        }

        // Si se da que todas son 0 o indeterminaciones se pasa a la ordenación por una regla auxiliar such as EDD
        if (best.operation == null || best.priority == 0) {
            return auxRule.run(operations);
        }

        return best.operation;
    }


    /**
     * Método que calcula la prioridad, es decir, la fórmula de la ATC.
     *
     * @param op operación de la cual calcular la prioridad
     * @param weight peso del trabajo al que pertenece la operación
     * @param dueDate fecha límite del trabajo al que pertenece la operación
     * @return la prioridad calculada
     */
    private double function(Operation op, double weight, int dueDate) {

        // Calcular el tiempo de completitud del trabajo:
        long startingTime = op.getStartTime();
        long processingTime = op.getProcessingTime();


        // La base:
        if (processingTime == 0) {
            return 0;
        }

        double base = weight / processingTime;
        String baseString = df.format(base);

        baseString = baseString.replace(',','.');
        base = Double.parseDouble(baseString);

        // El exponente:
        long numerador = Math.max(dueDate - processingTime - startingTime, 0);
        double denominador = kValue * getAvgProcessingTime();

        double exp;
        if (denominador == 0)
            exp = 0;
        else
            exp = - numerador / denominador;


        // Calcular la suma de base + exponente
        double priority = base * Math.exp(exp);

        return priority;
    }

    /**
     * Método que calcula la media de tiempo de procesamiento de las tareas no planificadas.
     *
     * @return media de tiempos
     */
    private double getAvgProcessingTime() {
        long totalProcessingTime = 0;
        int countNotScheduled = 0;

        for (Job j: jobs) {
            int auxProcessingTime = 0;
            for (int i = j.getOperations().size() - 1; i >= 0; i--) {
                Operation o = j.getOperations().get(i);
                if (!o.isScheduled()) {
                    auxProcessingTime += o.getProcessingTime();
                    countNotScheduled++;
                }
                else {
                    break;
                }
            }
            totalProcessingTime += auxProcessingTime;
        }

        double avgPt;

        if (countNotScheduled == 0)
            avgPt = 0;
        else
            avgPt = totalProcessingTime / countNotScheduled;

        return avgPt;
    }

}
