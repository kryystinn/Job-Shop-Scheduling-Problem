package logic.schedule.rules.impl;

import logic.instances.Operation;
import logic.schedule.rules.Rule;

import java.util.List;

public class MCTRule implements Rule {

    class MaxOperation {
        double priority;
        Operation operation;

        MaxOperation(Operation op, double priority) {
            this.operation = op;
            this.priority = priority;
        }

    }

    public Operation run(List<Operation> operations) {
        // Comprobar que la lista no está vacía
        if(operations.isEmpty())  return null;


        MaxOperation best = new MaxOperation(null, 0.0);

        for (Operation o :operations) {

            double priority = 1.0 / Double.valueOf(o.getStartTime() + o.getProcessingTime());

            if (priority > best.priority) {
                best = new MaxOperation(o, priority);
            }

        }

        if (best == null || best.operation == null || best.priority == 0) {
            return operations.get(0);
        }

        return best.operation;
    }


}
