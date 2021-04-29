package logic.schedule.algorithm.impl;

import logic.exceptions.AlgorithmException;
import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.Operation;
import logic.instances.ResultTask;
import logic.schedule.algorithm.ScheduleAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class GTAlgorithm implements ScheduleAlgorithm {

    private Instance inst;
    private List<Operation> setA;


    @Override
    public List<ResultTask> run(Instance data) throws AlgorithmException {
        ArrayList<ResultTask> results = new ArrayList<ResultTask>();
        this.inst = data;
        setA = new ArrayList<Operation>();

        getASet();



        //System.out.println("reached here successfully");
        
        return null;
    }

    private List<Operation> getASet(){
        setA.clear();

        // IF es la primera iteración que se hace:
//        for (Job j: inst.getJobs()) {
//            Operation first = j.getOperations().get(0);
//            setA.add(first);
//        }

        // ELSE: coger la primera operación SIN PLANIFICAR de cada Job
        for (Job j: inst.getJobs()) {
            for (Operation op: j.getOperations()) {
                if(!op.isScheduled()){
                    setA.add(op);
                    break;
                }
            }
        }

        System.out.println(setA.toString());
        return null;
    }
}
