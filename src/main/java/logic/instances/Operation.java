package logic.instances;

public class Operation {

    private int processingTime;
    private int nMachine;
    private boolean isScheduled;

    public Operation(int processingTime, int nMachine) {
        this.processingTime = processingTime;
        this.nMachine = nMachine;
        this.isScheduled = false;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getnMachine() {
        return nMachine;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void scheduled(){
        isScheduled = true;
    }

    public String toString() {
        return getProcessingTime() + " " + getnMachine() + "\t\t";
    }

}
