package logic.instances;
import logic.exceptions.ParserException;
import logic.parser.FileData;
import logic.parser.FileParser;

public class ResultTask {
    private long processingTime;
    private long startTime;
    private long endTime;
    private int nMachine;
    private int nJob;

    public ResultTask(long processingTime, long startTime, long endTime, int nMachine, int nJob) {
        this.processingTime = processingTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nMachine = nMachine;
        this.nJob = nJob;
    }

    public int getnJob() {
        return nJob;
    }

    public int getnMachine() {
        return nMachine;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public String toString() {
        return "Job id: " + getnJob() + " Machine id: " + getnMachine() + " " +  "Processing Time: " +
                getProcessingTime() + " Start Time: " + getStartTime() + " End Time: " + getEndTime();
    }
}