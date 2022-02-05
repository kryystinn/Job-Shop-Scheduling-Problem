package logic.schedule.algorithm;

import logic.exceptions.AlgorithmException;
import logic.instances.ResultTask;
import java.util.List;

/**
 * Interfaz ScheduleAlgorithm que representa un algoritmo de planificación.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public interface ScheduleAlgorithm {

    List<ResultTask> run() throws AlgorithmException;

    void writeStartingTimeMatrix(String path, String output, String sheet);

    void writeAll(String path, String output, String inst, int rowNum, int colNum,
                         boolean extended, String objFun);

}
