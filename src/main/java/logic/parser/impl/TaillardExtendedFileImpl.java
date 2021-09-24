package logic.parser.impl;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.taillard.TaillardInstance;

import java.util.ArrayList;
import java.util.List;

public class TaillardExtendedFileImpl extends FileParserImpl<TaillardInstance> {

    public TaillardExtendedFileImpl() {
    }

    public TaillardInstance parseFile(String data) {
        String[] lines = data.split("\n");

        String firstLine = lines[0];
        firstLine = firstLine.replaceAll(" +"," ").trim();

        String[] info = firstLine.split(" ");
        int nJobs = Integer.parseInt(info[0]);
        int nMachines = Integer.parseInt(info[1]);
        int nOperations = Integer.parseInt(info[2]);

        List<Job> jobs = new ArrayList<Job>();
        int jobCount = 1;
        for (int row = 1; row < nJobs+1; row++) {
            String line = lines[row].replaceAll(" +"," ").trim();
            String[] dataLine = line.split(" ");


            int dueDate = Integer.parseInt(dataLine[1]);
            double weight = Double.parseDouble(dataLine[2]);


            int[] times = new int[nMachines];
            int[] machines = new int[nMachines];
            int index = 0;
            for (int i = 4; i < dataLine.length; i += 2) {
                machines[index] = Integer.parseInt(dataLine[i]);
                times[index] = Integer.parseInt(dataLine[i+1]);
                index++;
            }

            int operationCount = 1;
            int beforeEndTime = 0;
            List<Operation> operationsPerJob = new ArrayList<Operation>();

            for (int j = 0; j < nMachines; j++) {
                operationsPerJob.add(new Operation(beforeEndTime, times[j], machines[j], jobCount, operationCount));
                operationCount++;
                beforeEndTime += times[j];
            }
            jobs.add(new Job(jobCount, operationsPerJob, dueDate, weight));
            jobCount++;
        }

        for (Job j: jobs) {
            System.out.println(j.toString() + j.getDueDate() + " // " + j.getWeight() + "         ");
        }

        return new TaillardInstance(nJobs, nMachines, jobs);
    }

}