package logic.schedule.algorithm;

import logic.exceptions.AlgorithmException;
import logic.instances.Instance;
import logic.instances.ResultTask;
import java.util.List;

public interface ScheduleAlgorithm {

    public List<ResultTask> run() throws AlgorithmException;

    public void writeOutput(String path, String output, String sheet);
}
