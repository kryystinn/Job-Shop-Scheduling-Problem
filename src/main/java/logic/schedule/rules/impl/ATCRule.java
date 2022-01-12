package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.text.DecimalFormat;
import java.util.*;

public class ATCRule implements Rule {

    private List<Job> jobs;
    private double kValue;
    private Rule auxRule;

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

    class MaxOperation {
        double priority;
        Operation operation;

        MaxOperation(Operation op, double priority) {
            this.operation = op;
            this.priority = priority;
        }

    }

    @Override
    public Operation run(List<Operation> operations) {

        // coger los pesos y las due dates de los jobs a los que pertenecen las operaciones del set B
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

        // si se da que todas son 0 o indeterminaciones se pasa a la ordenación por una regla auxiliar such as EDD
        if (best == null || best.operation == null || best.priority == 0) {
            return auxRule.run(operations);
        }

        return best.operation;
    }


    // función que calcula la prioridad (la fórmula itself de la atc) de cada job
    private double function(Operation op, double weight, int dueDate) {

        // Get the completion time of the job:
        long startingTime = op.getStartTime();
        long processingTime = op.getProcessingTime();


        // Then get the base:
        if (processingTime == 0) {
            return 0;
        }

        double base = weight / processingTime;
        String baseString = df.format(base);

        baseString = baseString.replace(',','.');
        base = Double.valueOf(baseString);

        // Then get the exponent:
        long numerador = Math.max(dueDate - processingTime - startingTime, 0);
        double denominador = kValue * getAvgProcessingTime();

        double exp;
        if (denominador == 0)
            exp = 0;
        else
            exp = - numerador / denominador;


        // Get the base + exp
        double priority = Double.valueOf(base * Math.exp(exp));

        return priority;
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

        double avgPt;

        if (countNotScheduled == 0)
            avgPt = 0;
        else
            avgPt = totalProcessingTime / countNotScheduled;

        return avgPt;

    }


}
