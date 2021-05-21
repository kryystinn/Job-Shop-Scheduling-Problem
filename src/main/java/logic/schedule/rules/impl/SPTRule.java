package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class SPTRule implements Rule {

    List<Operation> operations;

    public SPTRule(List<Operation> ops){
        this.operations = ops;
    }

    public Operation run(){
        // no debería llegar una lista vacía o null
        Operation shortestProcessTime = operations.get(0);
        for (Operation o :operations) {
            if (shortestProcessTime.getProcessingTime() > o.getProcessingTime()){
                shortestProcessTime = o;
            }
            // what if tienen el mismo processing time ???
        }

        return shortestProcessTime;
    }

}
