package logic.instances;

public abstract class Instance {

    private int nJobs;
    private int nMachines;

    public Instance(int nJobs, int nMach){
        this.nJobs = nJobs;
        this.nMachines = nMach;
    }

}
