package logic.parser.impl;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.taillard.TaillardInstance;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase TaillardFileImpl encargada de crear Instancias de Taillard.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class TaillardFileImpl extends FileParserImpl<TaillardInstance> {

    /**
     * Constructor de la clase {@link TaillardFileImpl}
     *
     */
    public TaillardFileImpl() {
    }


    /**
     * Devuelve una instancia de Taillard que crea en función de unos datos que se le pasan a través de un txt, el
     * cual tiene que tener los datos de la forma:
     *
     * Nb of jobs, Nb of Machines, Time seed, Machine seed, Upper bound, Lower bound
     * [nJobs] [nMachines] [timeSeed] [machineSeed] [upperBound] [lowerBound]
     * Times
     * [pTimeJ1Op1] [pTimeJ1Op2] [pTimeJ1Op3] ...
     * [pTimeJ2Op1] [pTimeJ2Op2] [pTimeJ2Op3] ...
     * ...
     *
     * Machines
     * [machineJ1Op1] [machineJ1Op2] [machineeJ1Op3] ...
     * [machineJ2Op1] [machineJ2Op2] [machineeJ2Op3] ...
     * ...
     *
     * @param data datos del fichero en cuestión
     * @return {@link TaillardInstance}
     */
    public TaillardInstance parseFile(String data) {
        String[] lines = data.split("\n");

        // First line of info
        String secondLine = lines[1];
        secondLine = secondLine.replaceAll(" +"," ").trim();

        String[] info = secondLine.split(" ");
        int nJobs = Integer.parseInt(info[0]);
        int nMachines = Integer.parseInt(info[1]);
        int timeSeed = Integer.parseInt(info[2]);
        int machineSeed = Integer.parseInt(info[3]);
        int upperBound = Integer.parseInt(info[4]);
        int lowerBound = Integer.parseInt(info[5]);


        // Times part
        List<int[]> jobTimes = new ArrayList<>();
        int totalProcessingTime = 0;
        for (int row = 3; row < nJobs+3; row++) {
            String line = lines[row].replaceAll(" +"," ").trim();
            String[] lineTimes = line.split(" ");
            int[] times = new int[nMachines];
            for (int i = 0; i < nMachines; i++) {
                times[i] = Integer.parseInt(lineTimes[i]);
                totalProcessingTime += times[i];
            }
            jobTimes.add(times);

        }

        // Machines part

        List<int[]> jobMachines = new ArrayList<>();
        for (int row = nJobs+4; row < nJobs*2+4; row++) {
            String line = lines[row].replaceAll(" +"," ").trim();
            String[] lineTimes = line.split(" ");
            int[] machines = new int[nMachines];
            for (int i = 0; i < nMachines; i++) {
                machines[i] = Integer.parseInt(lineTimes[i]);
            }
            jobMachines.add(machines);

        }

        // Create jobs and operations
        List<Job> jobs = new ArrayList<Job>();
        int jobCount = 1;
        int operationCount = 0;
        for (int i = 0; i < nJobs; i++) {
            List<Operation> operationsPerJob = new ArrayList<Operation>();
            int beforeEndTime = 0;
            for (int j = 0; j < nMachines; j++) {
                operationsPerJob.add(new Operation(beforeEndTime, jobTimes.get(i)[j], jobMachines.get(i)[j], jobCount, operationCount));
                operationCount++;
                beforeEndTime += jobTimes.get(i)[j];
            }
            jobs.add(new Job(jobCount, operationsPerJob));
            jobCount++;
        }

        return new TaillardInstance(nJobs, nMachines, timeSeed, machineSeed, upperBound, lowerBound, jobs, totalProcessingTime);
    }

}
