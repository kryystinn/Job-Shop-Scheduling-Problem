package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATCRule implements Rule {

    private List<Job> jobs;
    private double kValue;
    private Rule auxRule;
    private HashMap<Operation, Double> priorities;

    private static DecimalFormat df = new DecimalFormat("0.00");

    public ATCRule(Instance instance) {
        this(instance, 0.5, new EDDRule(instance));
    }

    public ATCRule(Instance instance, double kValue) {
        this.jobs = instance.getJobs();
        this.kValue = kValue;
        this.auxRule = new EDDRule(instance);
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
        long startingTime = op.getStartTime();
        long processingTime = op.getProcessingTime();


        // Then get the base:
        double base = weight / processingTime;
        String baseString = df.format(base);

        baseString = baseString.replace(',','.');

        base = Double.valueOf(baseString);

        // Then get the exponent:
        long numerador = Math.max(dueDate - processingTime - startingTime, 0);
        double denominador = kValue * getAvgProcessingTime();

        double exp = 0;
        if (denominador == 0)
            exp = 0;
        else
            exp = - numerador / denominador;


        // Get the base + exp
        double priority = Double.valueOf(base * Math.exp(exp));

        return priority;
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

        double avgPt = -1;

        if (countNotScheduled == 0)
            avgPt = 0;
        else
            avgPt = totalProcessingTime / countNotScheduled;

        return avgPt;

    }


}
