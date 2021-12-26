package logic.schedule;

import logic.exceptions.AlgorithmException;
import logic.instances.ResultTask;
import logic.schedule.algorithm.ScheduleAlgorithm;

import java.util.List;

public class ScheduleInstance {

    private ScheduleAlgorithm schedulingAlgorithm;
    private List<ResultTask> results;

    public ScheduleInstance(ScheduleAlgorithm algorithm) {
        this.schedulingAlgorithm = algorithm;
    }

    public List<ResultTask> executeAlgorithm() throws AlgorithmException {
        results = this.schedulingAlgorithm.run();
        return results;
    }

    public void generateOutput(String path, String output, String name) {
        this.schedulingAlgorithm.writeStartingTimeMatrix(path, output, name);
    }

    public void generateAllOutput(String path, String output, String inst, int rowNum, int colNum,
                                  boolean extended, String objFun){
        this.schedulingAlgorithm.writeAll(path, output, inst, rowNum, colNum, extended, objFun);
    }

}
