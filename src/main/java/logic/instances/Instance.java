package logic.instances;

import logic.exceptions.ParserException;

import java.util.ArrayList;
import java.util.List;

public abstract class Instance {

    private int nJobs;
    private int nMachines;
    private List<Machine> machines;

    public Instance(int nJobs, int nMach){
        this.nJobs = nJobs;
        this.nMachines = nMach;
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
        return nJobs;
    }

    public int getnMachines(){
        return nMachines;
    }

    public List<Machine> getMachines(){
        return machines;
    }

}
