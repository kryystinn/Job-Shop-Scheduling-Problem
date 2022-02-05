package logic.schedule;

import logic.exceptions.AlgorithmException;
import logic.instances.ResultTask;
import logic.schedule.algorithm.ScheduleAlgorithm;
import java.util.List;

/**
 * Clase ScheduleInstance que representa un planificador de instancias.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class ScheduleInstance {

    private final ScheduleAlgorithm schedulingAlgorithm;

    /**
     * Constructor de la clase {@link ScheduleInstance}
     *
     * @param algorithm algoritmo planificador a emplear
     */
    public ScheduleInstance(ScheduleAlgorithm algorithm) {
        this.schedulingAlgorithm = algorithm;
    }

    /**
     * Método que ejecuta el algoritmo planificador elegido.
     *
     * @return lista con las tareas planificadas
     * @throws AlgorithmException si falla la ejecución del algoritmo
     */
    public List<ResultTask> executeAlgorithm() throws AlgorithmException {
        return this.schedulingAlgorithm.run();
    }

    /**
     * Método que genera una salida con una matriz de tiempos de inicio de las tareas.
     *
     * @param path ruta
     * @param output fichero
     * @param name nombre de la hoja de cálculo
     */
    public void generateOutput(String path, String output, String name) {
        this.schedulingAlgorithm.writeStartingTimeMatrix(path, output, name);
    }

    /**
     * Método que genera una salida con una con los resultados de la función objetivo seleccionada.
     *
     * @param path ruta
     * @param output fichero
     * @param inst instancia
     * @param rowNum número de fila
     * @param colNum número de columna
     * @param extended true/false si es extendida o no
     * @param objFun función objetivo a analizar
     */
    public void generateAllOutput(String path, String output, String inst, int rowNum, int colNum,
                                  boolean extended, String objFun){
        this.schedulingAlgorithm.writeAll(path, output, inst, rowNum, colNum, extended, objFun);
    }

}
