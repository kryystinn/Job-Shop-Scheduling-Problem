package logic.instances;

/**
 * Clase Operation que representa una operación o tarea de un trabajo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class Operation {

    private int operationNumber;
    private int machineNumber;
    private int jobId;

    private long processingTime;
    private long startTime;
    private boolean isScheduled;


    /**
     * Constructor de la clase {@link Operation}.
     * Al principio de la planificación, todas las tareas tienen la propiedad {@link #isScheduled} en false.
     *
     * @param value único valor para todas las propiedades de la tarea
     */
    public Operation(int value) {
        this.processingTime = value;
        this.machineNumber = value;
        this.jobId = value;
        this.operationNumber = value;
        this.startTime = value;
        this.isScheduled = false;
    }

    /**
     * Constructor de la clase {@link Operation}.
     * Al principio de la planificación, todas las tareas tienen la propiedad {@link #isScheduled} en false.
     *
     * @param startTime momento en el tiempo en el que como mínimo puede comenzar a ejecutarse la tarea
     * @param processingTime tiempo de ejecución, es decir, unidades en el tiempo que tarda en ejecutarse la tarea
     * @param nMachine número de la máquina en la que tiene que ejecutarse la tarea
     * @param jobId número del trabajo al que pertenece la tarea
     * @param operationNumber número identificador de la tarea
     */
    public Operation(int startTime, int processingTime, int nMachine, int jobId, int operationNumber) {
        this.processingTime = processingTime;
        this.machineNumber = nMachine;
        this.jobId = jobId;
        this.operationNumber = operationNumber;
        this.startTime = startTime;
        this.isScheduled = false;
    }

    /**
     * Devuelve el valor del tiempo de ejecución de la tarea.
     *
     * @return {@link #processingTime}
     */
    public long getProcessingTime() {
        return this.processingTime;
    }

    /**
     * Devuelve el número de identificación de la tarea.
     *
     * @return {@link #operationNumber}
     */
    public int getOperationNumber() {
        return this.operationNumber;
    }

    /**
     * Devuelve el número de identificación del trabajo al que pertenece la tarea.
     *
     * @return {@link #jobId}
     */
    public int getJobId() {
        return this.jobId;
    }

    /**
     * Devuelve el número de la máquina en la que tiene que ejecutarse la tarea.
     *
     * @return {@link #machineNumber}
     */
    public int getMachineNumber() {
        return this.machineNumber;
    }

    /**
     * Devuelve true/false en función de si la tarea ya ha sido o no planificada.
     *
     * @return {@link #isScheduled}
     */
    public boolean isScheduled() {
        return this.isScheduled;
    }

    /**
     * Devuelve el momento en el tiempo en el que como mínimo puede comenzar a ejecutarse la tarea.
     *
     * @return {@link #startTime}
     */
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Devuelve el momento en el tiempo en el que una tarea habría finalizado su ejecución, calculado mediante la suma
     * del momento en el tiempo en el que comienza su ejecución y el tiempo de ejecución de la tarea.
     *
     * @return unidad de tiempo en el que una tarea finaliza su ejecución
     */
    public long getEndTime() {
        return this.startTime + this.processingTime;
    }

    /**
     * Planifica una tarea, haciendo que {@link #isScheduled} sea true y estableciendo el {@link #startTime} momento
     * en el tiempo en el que comienza la ejecución, ya que la tarea pudo haber sido creada con un startTime menor al
     * que realmente es a la hora de planificarse.
     *
     * @param newInitialTime nuevo tiempo de comienzo de la tarea
     */
    public void scheduleOperation(long newInitialTime) {
        this.startTime = newInitialTime;
        this.isScheduled = true;
    }

    /**
     * Asigna un nuevo valor de tiempo de comienzo de una tarea {@link #startTime}.
     *
     * @param newInitialTime nuevo tiempo de comienzo de la tarea
     */
    public void setStartTime(long newInitialTime) {
        this.startTime = newInitialTime;
    }

    public void resetOperation() {
        this.isScheduled = false;
    }

    public String toString() {
        return getProcessingTime() + " " + getMachineNumber() + "\t\t";
    }

}
