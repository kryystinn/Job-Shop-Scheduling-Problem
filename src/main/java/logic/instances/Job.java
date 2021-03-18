package logic.instances;

import java.util.List;

public class Job {

    private int n1;
    private int n2;
    private double n3;
    private int n4;
    private List<Operation> sets;

    public Job(int n1, int n2, double n3, int n4, List<Operation> operations) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
        this.sets = operations;
    }

}
