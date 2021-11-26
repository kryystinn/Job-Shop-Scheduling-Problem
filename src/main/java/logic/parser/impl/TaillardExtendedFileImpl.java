package logic.parser.impl;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.taillard.TaillardInstance;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase TaillardExtendedFileImpl encargada de crear Instancias de Taillard extended.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class TaillardExtendedFileImpl extends FileParserImpl<TaillardInstance> {

    /**
     * Constructor de la clase {@link TaillardExtendedFileImpl}
     *
     */
    public TaillardExtendedFileImpl() {
    }


    /**
     * Devuelve una instancia de Taillard que crea en función de unos datos que se le pasan a través de un txt, el
     * cual tiene que tener los datos de la forma:
     *
     * [nJobs] [nMachines] [nOperations]
     * [releaseDateJ1] [dueDateJ1] [weightJ1] [nOperationsJ1] [machineJ1Op1] [pTimeJ1Op1] [machineJ1Op2] [pTimeJ1Op2]...
     * [releaseDateJ2] [dueDateJ2] [weightJ2] [nOperationsJ2] [machineJ2Op1] [pTimeJ2Op1] [machineJ2Op2] [pTimeJ2Op2]...
     * ...
     *
     * @param data datos del fichero en cuestión
     * @return {@link TaillardInstance}
     */
    public TaillardInstance parseFile(String data) {
        String[] lines = data.split("\n");

        String firstLine = lines[0];
        firstLine = firstLine.replaceAll(" +"," ").trim();

        // First line data
        String[] info = firstLine.split(" ");
        int nJobs = Integer.parseInt(info[0]);
        int nMachines = Integer.parseInt(info[1]);
        int nOperations = Integer.parseInt(info[2]);

        // Each job
        List<Job> jobs = new ArrayList<Job>();
        int jobCount = 1;
        int totalProcessingTime = 0;
        for (int row = 1; row < nJobs+1; row++) {
            String line = lines[row].replaceAll(" +"," ").trim();
            String[] dataLine = line.split(" ");

            // Job data
            // int releaseDate = Integer.parseInt(dataLine[0]);
            int dueDate = Integer.parseInt(dataLine[1]);
            double weight = Double.parseDouble(dataLine[2]);

            // Saving times and machines of the job in 2 lists
            int operationCount = 1;
            int beforeEndTime = 0;
            List<Operation> operationsPerJob = new ArrayList<Operation>();

            for (int i = 4; i < dataLine.length; i += 2) {
                int machineNum = Integer.parseInt(dataLine[i]);

                int procTime = Integer.parseInt(dataLine[i+1]);

                operationsPerJob.add(new Operation(beforeEndTime, procTime,
                       machineNum, jobCount, operationCount));

                operationCount++;
                beforeEndTime += procTime;
                totalProcessingTime += procTime;
            }

            jobs.add(new Job(jobCount, operationsPerJob, dueDate, weight));
            jobCount++;

        }

        return new TaillardInstance(nJobs, nMachines, jobs, totalProcessingTime);
    }

}