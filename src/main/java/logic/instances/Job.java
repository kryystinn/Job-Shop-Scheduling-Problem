package logic.instances;

import java.util.List;

public class Job {
    private int id;
    private List<Operation> operations;

    public Job(int id, List<Operation> operations) {
        this.id = id;
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public int getJobId() {
        return id;
    }

}
