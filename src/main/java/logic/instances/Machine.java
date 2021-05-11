package logic.instances;

public class Machine {

    private int machineNumber;
    private boolean isBusy;

    public Machine(int id){
        this.machineNumber = id;
        isBusy = false;
    }

    public int getMachineNumber(){
        return this.machineNumber;
    }

    public boolean isBusy(){
        return this.isBusy;
    }
}
