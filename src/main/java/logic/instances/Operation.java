package logic.instances;

public class Operation {

    private int operationNumber;
    private int machineNumber;
    private int jobNumber;

    private long processingTime;
    private long startingTime;
    private boolean isScheduled;

    public Operation(int value) {
        this.processingTime = value;
        this.machineNumber = value;
        this.jobNumber = value;
        this.operationNumber = value;
        this.startingTime = value;
        this.isScheduled = false;
    }

    public Operation(int initialTime, int processingTime, int nMachine, int jobNumber, int operationNumber) {
        this.processingTime = processingTime;
        this.machineNumber = nMachine;
        this.jobNumber = jobNumber;
        this.operationNumber = operationNumber;
        this.startingTime = initialTime;
        this.isScheduled = false;
    }

    public long getProcessingTime() {
        return this.processingTime;
    }

    public int getOperationNumber() {
        return this.operationNumber;
    }

    public int getJobNumber() {
        return this.jobNumber;
    }

    public int getMachineNumber() {
        return this.machineNumber;
    }

    public boolean isScheduled() {
        return this.isScheduled;
    }

    public long getStartingTime() {
        return this.startingTime;
    }

    public long getEndTime() {
        return this.startingTime + this.processingTime;
    }

    public void scheduleOperation(long newInitialTime) {
        this.startingTime = newInitialTime;
        this.isScheduled = true;
    }

    public void setStartingTime(long newInitialTime) {
        this.startingTime = newInitialTime;
    }

    public String toString() {
        return getProcessingTime() + " " + getMachineNumber() + "\t\t";
    }

}
