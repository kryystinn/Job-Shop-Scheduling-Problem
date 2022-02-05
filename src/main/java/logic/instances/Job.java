package logic.instances;

import java.io.Serializable;
import java.util.List;

/**
 * Clase Job que representa un trabajo.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class Job implements Serializable {

    private final int jobId;
    private final List<Operation> operations;
    private int dueDate;
    private double weight;

    /**
     * Constructor de la clase {@link Job}.
     *
     * @param id identificador del trabajo
     * @param operations lista de operaciones del trabajo
     */
    public Job(int id, List<Operation> operations) {
        this.jobId = id;
        this.operations = operations;
    }

    /**
     * Constructor de la clase {@link Job}.
     *
     * @param id identificador del trabajo
     * @param operations lista de operaciones del trabajo
     * @param dueDate fecha límite del trabajo
     * @param weight peso del trabajo
     */
    public Job(int id, List<Operation> operations, int dueDate, double weight) {
        this.jobId = id;
        this.operations =  operations;
        this.dueDate = dueDate;
        this.weight = weight;
    }

    /**
     * Getter del listado de operaciones del trabajo.
     *
     * @return {@link #operations}
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * Getter del id del trabajo.
     *
     * @return {@link #jobId}
     */
    public int getJobId() {
        return jobId;
    }

    /**
     * Getter de la fecha límite del trabajo.
     *
     * @return {@link #dueDate}
     */
    public int getDueDate() {
        return this.dueDate;
    }

    /**
     * Getter del peso del trabajo.
     *
     * @return {@link #weight}
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Método que resetea todas las operaciones del trabajo.
     *
     */
    public void resetOperations() {
        for (Operation o: operations) {
            o.resetOperation();
        }
    }

    public String toString() {
        return getJobId() + " ";
    }

}
