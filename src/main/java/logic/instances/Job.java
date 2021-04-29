package logic.instances;

import java.util.List;

public class Job {

    private List<Operation> operations;

    public Job( List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

}
