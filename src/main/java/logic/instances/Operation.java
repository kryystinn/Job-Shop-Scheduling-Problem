package logic.instances;

public class Operation {

    private int processingTime;
    private int machineNumber;
    private int initialTime;
    private int endTime;
    private boolean isScheduled;

    public Operation(int processingTime, int nMachine) {
        this.processingTime = processingTime;
        this.machineNumber = nMachine;
        this.endTime = processingTime;
        this.isScheduled = false;
    }

    public int getProcessingTime() {
        return this.processingTime;
    }

    public int getMachineNumber() {
        return this.machineNumber;
    }

    public boolean isScheduled() {
        return this.isScheduled;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void scheduleOperation(int newInitialTime){
        this.initialTime = newInitialTime;
        this.endTime = this.initialTime + this.processingTime;
        this.isScheduled = true;
    }

    public String toString() {
        return getProcessingTime() + " " + getMachineNumber() + "\t\t";
    }

}
