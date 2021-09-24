package logic.parser.impl;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.taillard.TaillardInstance;

import java.util.ArrayList;
import java.util.List;

public class TaillardFileImpl extends FileParserImpl<TaillardInstance> {

    public TaillardFileImpl() {
    }

    public TaillardInstance parseFile(String data) {
        String[] lines = data.split("\n");

        // first line of info (which would be second line of input)
        String secondLine = lines[1];
        secondLine = secondLine.replaceAll(" +"," ").trim();

        String[] info = secondLine.split(" ");
        int nJobs = Integer.parseInt(info[0]);
        int nMachines = Integer.parseInt(info[1]);
        int timeSeed = Integer.parseInt(info[2]);
        int machineSeed = Integer.parseInt(info[3]);
        int upperBound = Integer.parseInt(info[4]);
        int lowerBound = Integer.parseInt(info[5]);

        System.out.println(nJobs + " " + nMachines + " " + timeSeed+ " " + machineSeed + " " + upperBound + " " + lowerBound);


        // Times part
        List<int[]> jobTimes = new ArrayList<>();
        for (int row = 3; row < nJobs+3; row++) {
            String line = lines[row].replaceAll(" +"," ").trim();
            String[] lineTimes = line.split(" ");
            int[] times = new int[nMachines];
            for (int i = 0; i < nMachines; i++) {
                times[i] = Integer.parseInt(lineTimes[i]);
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
        int jobCount = 0;
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

        for (Job j: jobs) {
            for (Operation o: j.getOperations()) {
                System.out.print(o.toString());
            }
            System.out.println();
        }

        return new TaillardInstance(nJobs, nMachines, timeSeed, machineSeed, upperBound, lowerBound, jobs);
    }

}
