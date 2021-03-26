package logic.instances;

public class Operation {

    private int processingTime;
    private int nMachine;

    public Operation(int processingTime, int nMachine) {
        this.processingTime = processingTime;
        this.nMachine = nMachine;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getnMachine() {
        return nMachine;
    }

    public String toString(){
        return getProcessingTime() + " " + getnMachine() + "\t\t";
    }

}
