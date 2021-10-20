package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATCRule implements Rule {

    private List<Job> jobs;
    private double kValue;
    private Rule auxRule;
    private HashMap<Operation, Double> priorities;

    public ATCRule(Instance instance) {
        this(instance, 1, new EDDRule(instance));
    }

    public ATCRule(Instance instance, double kValue, Rule auxRule) {
        this.jobs = instance.getJobs();
        this.kValue = kValue;
        this.auxRule = auxRule;
    }

    @Override
    public Operation run(List<Operation> operations) {

        priorities = new HashMap<>();

        // coger los pesos y las due dates de los jobs a los que pertenecen las operaciones del set B
        for (Operation op : operations) {
            for (Job j : jobs) {
                if (op.getJobId() == j.getJobId()) {
                    double weight = j.getWeight();
                    int dueDate = j.getDueDate();

                    double priority = function(op, weight, dueDate);

                    // se guardan las operaciones junto con su prioridad (double)
                    priorities.put(op,priority);

                }
            }

        }

        // se saca el entry con MAYOR prioridad de todas
        Map.Entry<Operation, Double> opMaxPriority = maxUsingIteration(priorities);
        // si se da que todas son 0 o indeterminaciones se pasa a la ordenación por una regla auxiliar such as EDD
        if (opMaxPriority == null || opMaxPriority.getValue() == 0) {
            return auxRule.run(operations);
        }

        return opMaxPriority.getKey();
    }


    // función que calcula la prioridad (la fórmula itself de la atc) de cada job
    private double function(Operation op, double weight, int dueDate) {

        // Get the completion time of the job:
        long startingTime = op.getStartingTime();
        long processingTime = op.getProcessingTime();
        long completionTime = dueDate - startingTime - processingTime;

        // Get the tardiness of the job:
        long tardiness = Math.max(completionTime - dueDate, 0);

        // Then get the tardiness weight:
        double weightedTardiness = weight * tardiness;

        // Then get the base:
        double base = weightedTardiness / processingTime;

        // Then get the exponent:


        return 0;
    }


    // coge
    private <K, V extends Comparable<V>> Map.Entry<K,V> maxUsingIteration(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry == null ? null: maxEntry;
    }

    private double getProcessingTimeAverage() {

        long totalProcessingTime = 0;
        int countNotScheduled = 0;

        for (Job j: jobs) {
            int auxProcessingTime = 0;
            for (Operation o: j.getOperations()) {

                if (!o.isScheduled()) {
                    auxProcessingTime += o.getProcessingTime();
                }

            }

            if (auxProcessingTime != 0) {
                countNotScheduled++;
                totalProcessingTime += auxProcessingTime;
            }

        }

        return totalProcessingTime / countNotScheduled;

    }


}
