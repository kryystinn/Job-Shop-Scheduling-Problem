package logic.instances;

import java.util.ArrayList;
import java.util.List;

public class TaillardInstance {

    private int nJobs;
    private int nMachines;
    private int timeSeed;
    private int machineSeed;
    private int upperBound;
    private int lowerBound;
    private List<Job> jobs;

    public TaillardInstance(int nJobs, int nMachines, int timeSeed, int machineSeed, int upperBound, int lowerBound, List<Job> jobs) {
        this.nJobs = nJobs;
        this.nMachines = nMachines;
        this.timeSeed = timeSeed;
        this.machineSeed = machineSeed;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.jobs = jobs;
    }
}
