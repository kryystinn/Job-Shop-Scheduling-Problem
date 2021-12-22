package logic.instances;

import java.util.List;

public class Job {
    private int id;
    private List<Operation> operations;
    private int dueDate;
    private double weight;

    public Job(int id, List<Operation> operations) {
        this.id = id;
        this.operations = operations;
    }

    public Job(int id, List<Operation> operations, int dueDate, double weight) {
        this.id = id;
        this.operations = operations;
        this.dueDate = dueDate;
        this.weight = weight;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public int getJobId() {
        return id;
    }

    public int getDueDate() {
        return this.dueDate;
    }

    public double getWeight() {
        return this.weight;
    }

    public void resetOperations() {
        for (Operation o: operations) {
            o.resetOperation();
        }
    }

    public String toString() {
        return getJobId() + " ";
    }

}
