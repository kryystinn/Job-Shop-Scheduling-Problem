package logic.instances.taillard;

import logic.instances.Instance;
import logic.instances.Job;

import java.util.Collections;
import java.util.List;

/**
 * Clase TaillardInstance que representa una instancia de Taillard. Extiende de la clase abstracta {@link Instance}.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class TaillardInstance extends Instance {

    private int timeSeed;
    private int machineSeed;
    private int upperBound;
    private List<Job> jobs;


    /**
     * Constructor de la clase {@link TaillardInstance}
     *
     * @param nJobs número de trabajos de la instancia
     * @param nMachines número de máquinas de la instancia
     * @param jobs listado de trabajos de la istancia
     * @param totalProcessingTime tiempo de procesamiento total de todas las tareas que forman la instancia
     */
    public TaillardInstance(int nJobs, int nMachines, List<Job> jobs, int totalProcessingTime) {
        super(nJobs, nMachines, totalProcessingTime, -1);
        this.jobs = jobs;
        Collections.sort(this.jobs);
    }

    /**
     * Constructor de la clase {@link TaillardInstance}
     *
     * @param nJobs número de trabajos de la instancia
     * @param nMachines número de máquinas de la instancia
     * @param timeSeed semilla del tiempo de la instancia
     * @param machineSeed semilla de la máquina de la instancia
     * @param upperBound cota superior de la instancia
     * @param lowerBound cota inferior de la instancia
     * @param jobs listado de trabajos de la istancia
     * @param totalProcessingTime tiempo de procesamiento total de todas las tareas que forman la instancia
     */
    public TaillardInstance(int nJobs, int nMachines, int timeSeed, int machineSeed, int upperBound, int lowerBound, List<Job> jobs, int totalProcessingTime) {
        super(nJobs, nMachines, totalProcessingTime, lowerBound);
        this.timeSeed = timeSeed;
        this.machineSeed = machineSeed;
        this.upperBound = upperBound;
        this.jobs = jobs;
    }

    /**
     * Devuelve el listado de trabajos de la instancia.
     *
     * @return {@link #jobs}
     */
    @Override
    public List<Job> getJobs() {
        return jobs;
    }


}
