package logic.instances;
import logic.exceptions.ParserException;
import logic.parser.FileData;
import logic.parser.FileParser;

public class ResultTask {
    private long processingTime;
    private long startTime;
    private long endTime;
    private int nMachine;

    public ResultTask(long processingTime, long startTime, long endTime, int nMachine) {
        this.processingTime = processingTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nMachine = nMachine;
    }
}