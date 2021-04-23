package logic.instances.taillard;

import logic.instances.Instance;
import logic.instances.Job;

import java.util.ArrayList;
import java.util.List;

public class TaillardInstance extends Instance {

    private int timeSeed;
    private int machineSeed;
    private int upperBound;
    private int lowerBound;
    private List<Job> jobs;

    public TaillardInstance(int nJobs, int nMachines, int timeSeed, int machineSeed, int upperBound, int lowerBound, List<Job> jobs) {
        super(nJobs, nMachines);
        this.timeSeed = timeSeed;
        this.machineSeed = machineSeed;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.jobs = jobs;
    }
}
