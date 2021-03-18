package logic.parser.impl;

import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.TaillardInstance;
import logic.parser.FileParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TaillardFileImpl extends FileParserImpl<TaillardInstance> {

    public TaillardFileImpl() {
    }

    public TaillardInstance parseFile(String data) {
        String[] lines = data.split("\n");

        // first line
        String[] firstLine = lines[0].split(" ");
        int nJobs = Integer.parseInt(firstLine[0]);
        int nMachines = Integer.parseInt(firstLine[1]);
        int nSets = Integer.parseInt(firstLine[2]);

        int operations = nSets / nJobs;
        List<Job> jobs = new ArrayList<Job>();

        // rest
        for(int row = 1; row <= nJobs; row++) {
            String[] line = lines[row].split(" ");
            int n1 = Integer.parseInt(line[0]);
            int n2 = Integer.parseInt(line[1]);
            double n3 = Double.parseDouble(line[2]);
            int n4 = Integer.parseInt(line[3]);
;
            List<Operation> operationsPerJob = new ArrayList<Operation>();
            for(int seq = 4; seq < 4 + operations * 2; seq += 2) {
                int machine = Integer.parseInt(line[seq]);
                int processingTime = Integer.parseInt(line[seq+1]);
                operationsPerJob.add(new Operation(machine, processingTime));
            }

            jobs.add(new Job(n1, n2, n3, n4, operationsPerJob));
        }

        return new TaillardInstance(nJobs, nMachines, nSets, jobs);
    }

}
