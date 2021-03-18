package logic.instances;

import java.util.ArrayList;
import java.util.List;

public class TaillardInstance {

    private int nJobs;
    private int nMachines;
    private int nSetsMachineProcTime;
    private List<Job> jobs;

    public TaillardInstance(int nJobs, int nMachines, int nSets, List<Job> jobs) {
        this.nJobs = nJobs;
        this.nMachines = nMachines;
        this.nSetsMachineProcTime = nSets;
        this.jobs = jobs;
    }
}
