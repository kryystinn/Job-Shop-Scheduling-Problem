package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class SPTRule implements Rule {


    public Operation run(List<Operation> operations) {

        if(operations.isEmpty())  return null;
        // no debería llegar una lista vacía o null
        Operation shortestProcessTime = operations.get(0);
        for (Operation o :operations) {
            if (shortestProcessTime.getProcessingTime() > o.getProcessingTime()){
                shortestProcessTime = o;
            }

        }

        return shortestProcessTime;
    }

}
