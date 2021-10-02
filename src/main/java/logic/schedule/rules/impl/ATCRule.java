package logic.schedule.rules.impl;

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

    public ATCRule(List<Job> jobs) {
        this(jobs, 1, new EDDRule(jobs));
    }

    public ATCRule(List<Job> jobs, double kValue, Rule auxRule) {
        this.jobs = jobs;
        this.kValue = kValue;
        this.auxRule = auxRule;
    }

    @Override
    public Operation run(List<Operation> operations) {

        priorities = new HashMap<>();

        for (Operation op : operations) {
            for (Job j : jobs) {
                if (op.getJobId() == j.getJobId()) {
                    double weight = j.getWeight();
                    int dueDate = j.getDueDate();

                    double priority = function(op, weight, dueDate);

                    priorities.put(op,priority);

                }
            }

        }

        Map.Entry<Operation, Double> opMaxPriority = maxUsingIteration(priorities);
        if (opMaxPriority == null || opMaxPriority.getValue() == 0) {
            return auxRule.run(operations);
        }

        return opMaxPriority.getKey();
    }

    private double function(Operation op, double weight, int dueDate) {

        double weightedTardiness = weight; // weight * ( max(Completion time of job - Due date of job, 0) )
        long processingTime = op.getProcessingTime();




        return 0;
    }

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
