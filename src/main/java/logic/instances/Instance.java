package logic.instances;

import logic.exceptions.ParserException;

import java.util.List;

public abstract class Instance {

    private int nJobs;
    private int nMachines;

    public Instance(int nJobs, int nMach){
        this.nJobs = nJobs;
        this.nMachines = nMach;
    }

    public abstract List<Job> getJobs();

    public int getnJobs(){
        return nJobs;
    }

    public int getnMachines(){
        return nMachines;
    }

}
