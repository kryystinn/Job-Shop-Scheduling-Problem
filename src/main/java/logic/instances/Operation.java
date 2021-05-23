package logic.instances;

public class Operation {

    private int operationNumber;
    private int machineNumber;
    private int jobNumber;

    private int processingTime;
    private int initialTime;
    private int endTime;
    private boolean isScheduled;

    public Operation(int value) {
        this.processingTime = value;
        this.machineNumber = value;
        this.jobNumber = value;
        this.operationNumber = value;
        this.endTime = value;
        this.isScheduled = false;
    }

    public Operation(int processingTime, int nMachine, int jobNumber, int operationNumber) {
        this.processingTime = processingTime;
        this.machineNumber = nMachine;
        this.jobNumber = jobNumber;
        this.operationNumber = operationNumber;
        this.endTime = processingTime;
        this.isScheduled = false;
    }

    public int getProcessingTime() {
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
