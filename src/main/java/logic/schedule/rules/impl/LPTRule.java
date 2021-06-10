package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class LPTRule implements Rule {
    public Operation run(List<Operation> operations){
        if(operations.isEmpty())  return null;
        // no debería llegar una lista vacía o null
        Operation longestProcessTime = operations.get(0);
        for (Operation o :operations) {
            if (longestProcessTime.getProcessingTime() < o.getProcessingTime()){
                longestProcessTime = o;
            }
            // qué pasa si tienen el mismo processing time ???
        }

        return longestProcessTime;
    }
}
