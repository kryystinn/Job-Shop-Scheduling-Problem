package logic.instances;

import java.io.Serializable;

/**
 * Clase ResultTask que representa una operación o tarea ya planificada de un trabajo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class ResultTask implements Serializable {

    private final long processingTime;
    private final long startTime;
    private final long endTime;
    private final int nMachine;
    private final int jobId;


    /**
     * Constructor de la clase {@link ResultTask}.
     *
     * @param processingTime tiempo de ejecución de la tarea planificada
     * @param startTime tiempo de comienzo de la tarea planificada
     * @param endTime tiempo de finalización de la tarea planificada
     * @param nMachine número de la máquina en la que se ejecutó la tarea
     * @param nJob número del trabajo al que pertenece la tarea planificada
     */
    public ResultTask(long processingTime, long startTime, long endTime, int nMachine, int nJob) {
        this.processingTime = processingTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nMachine = nMachine;
        this.jobId = nJob;
    }

    /**
     * Devuelve el número de identificación del trabajo al que pertenece la tarea.
     *
     * @return {@link #jobId}
     */
    public int getJobId() {
        return jobId;
    }

    /**
     * Devuelve el número de la máquina en la que se ejecutó la tarea.
     *
     * @return {@link #nMachine}
     */
    public int getnMachine() {
        return nMachine;
    }

    /**
     * Devuelve el momento en el tiempo en el que una tarea finalizó su ejecución.
     *
     * @return unidad de tiempo en el que una tarea finalizó su ejecución
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Devuelve el momento en el tiempo en el comenzó a ejecutarse la tarea.
     *
     * @return {@link #startTime}
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Devuelve el valor del tiempo de ejecución de la tarea.
     *
     * @return {@link #processingTime}
     */
    public long getProcessingTime() {
        return processingTime;
    }

    public String toString() {
        return "Job id: " + getJobId() + " Machine id: " + getnMachine() + " " +  "Processing Time: " +
                getProcessingTime() + " Start Time: " + getStartTime() + " End Time: " + getEndTime();
    }

    public String toStringStartTimes(){
        return "" + getStartTime();
    }
}