package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class MCMRule implements Rule {

    public Operation run(List<Operation> operations) {

        if(operations.isEmpty())  return null;
        // no debería llegar una lista vacía o null

        Operation minCompTime = operations.get(0);
        for (Operation o :operations) {
            if (minCompTime.getStartTime() + minCompTime.getProcessingTime() > o.getStartTime() + o.getProcessingTime()){
                minCompTime = o;
            }
        }

        return minCompTime;
    }
}
