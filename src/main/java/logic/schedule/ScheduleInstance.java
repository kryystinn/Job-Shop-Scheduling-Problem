package logic.schedule;

import logic.exceptions.AlgorithmException;
import logic.instances.Instance;
import logic.instances.ResultTask;
import logic.schedule.algorithm.ScheduleAlgorithm;

import java.util.List;

public class ScheduleInstance {

    private ScheduleAlgorithm schedulingAlgorithm;
    private List<ResultTask> results;

    public ScheduleInstance(ScheduleAlgorithm algorithm){
        this.schedulingAlgorithm = algorithm;
    }

    public List<ResultTask> executeAlgorithm() throws AlgorithmException {
        results = this.schedulingAlgorithm.run();
        return results;
    }
}
