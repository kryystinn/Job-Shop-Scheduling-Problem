package logic.instances;

import logic.exceptions.ParserException;

import java.util.ArrayList;
import java.util.List;

public abstract class Instance {

    private int nJobs;
    private int nMachines;
    private List<Machine> machines;
    private int totalProcessingTime;

    public Instance(int nJobs, int nMach, int totalProcTime){
        this.nJobs = nJobs;
        this.nMachines = nMach;
        this.totalProcessingTime = totalProcTime;
        createMachines();
    }

    public abstract List<Job> getJobs();

    private void createMachines(){
        machines = new ArrayList<Machine>();
        for (int i = 0; i < nMachines; i++){
            machines.add(new Machine(i));
        }
    }

    public int getnJobs(){
        return this.nJobs;
    }

    public int getnMachines(){
        return this.nMachines;
    }

    public int getTotalProcessingTime() {
        return this.totalProcessingTime;
    }

    public List<Machine> getMachines(){
        return machines;
    }

}
