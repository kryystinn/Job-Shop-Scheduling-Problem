package logic.schedule.rules.impl;

import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class EDDRule implements Rule {

    private List<Job> jobs;

    private static final int HIGH_VALUE = Integer.MAX_VALUE;

     public EDDRule(Instance instance){
        this.jobs = instance.getJobs();
    }

    public Operation run(List<Operation> operations){
        if(operations.isEmpty())  return null;
        // no debería llegar una lista vacía o null

        int opJobHasEarliestDueDate = HIGH_VALUE;
        Operation opToSchedule = operations.get(0);
        for (Operation op: operations) {
            for (Job j: jobs) {
                if (op.getJobId() == j.getJobId()) {
                    if (j.getDueDate() < opJobHasEarliestDueDate){
                        opJobHasEarliestDueDate = j.getDueDate();
                        opToSchedule = op;

                    }
                    else if (j.getDueDate() == opJobHasEarliestDueDate){
                        if (op.getProcessingTime() < opToSchedule.getProcessingTime())
                            opToSchedule = op;
                    }
                }
            }
        }
        return opToSchedule;
    }


}
