package logic.instances;

import logic.exceptions.ParserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta Instance que representa una instancia, es decir, el problema a planificar.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public abstract class Instance implements Serializable {

    private int nJobs;
    private int nMachines;
    private List<Machine> machines;
    private int totalProcessingTime;
    private int lowerBound;
    private int upperBound;

    /**
     * Constructor de la clase {@link Instance}.
     *
     * @param nJobs número de trabajos de la instancia
     * @param nMach número de máquinas de la instancia
     * @param totalProcTime tiempo de procesamiento total de todas las tareas que forman la instancia
     */
    public Instance(int nJobs, int nMach, int totalProcTime){
        this.nJobs = nJobs;
        this.nMachines = nMach;
        this.totalProcessingTime = totalProcTime;
        createMachines();
    }

    /**
     * Constructor de la clase {@link Instance}.
     *
     * @param nJobs número de trabajos de la instancia
     * @param nMach número de máquinas de la instancia
     * @param totalProcTime tiempo de procesamiento total de todas las tareas que forman la instancia
     * @param lowerBound cota inferior de la instancia, si no tiene será un valor de -1
     */
    public Instance(int nJobs, int nMach, int totalProcTime, int lowerBound, int upperBound){
        this.nJobs = nJobs;
        this.nMachines = nMach;
        this.totalProcessingTime = totalProcTime;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        createMachines();
    }

    public abstract List<Job> getJobs();

    /**
     * Método que crea un listado de máquinas de clase {@link Machine} que conforman la instancia a planificar,
     * asignándoles a cada una de ellas un id.
     *
     */
    private void createMachines(){
        machines = new ArrayList<Machine>();
        for (int i = 0; i < nMachines; i++){
            machines.add(new Machine(i));
        }
    }

    /**
     * Devuelve el número de trabajos de la instancia.
     *
     * @return {@link #nJobs}
     */
    public int getnJobs(){
        return this.nJobs;
    }

    /**
     * Devuelve el número de máquinas de la instancia.
     *
     * @return {@link #nMachines}
     */
    public int getnMachines(){
        return this.nMachines;
    }

    /**
     * Devuelve el tiempo de procesamiento total de la instancia.
     *
     * @return {@link #totalProcessingTime}
     */
    public int getTotalProcessingTime() {
        return this.totalProcessingTime;
    }

    /**
     * Devuelve la cota inferior de la instancia.
     *
     * @return {@link #lowerBound}
     */
    public int getLowerBound(){
        return this.lowerBound;
    }

    /**
     * Devuelve la cota superior de la instancia.
     *
     * @return {@link #upperBound}
     */
    public int getUpperBound(){
        return this.upperBound;
    }

    /**
     * Devuelve el listado con las máquinas que forman la instancia.
     *
     * @return {@link #machines}
     */
    public List<Machine> getMachines(){
        return machines;
    }

}
