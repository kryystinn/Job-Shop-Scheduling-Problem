package logic.schedule.algorithm.impl;

import logic.exceptions.AlgorithmException;
import logic.instances.*;
import logic.schedule.algorithm.ScheduleAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class GTAlgorithm implements ScheduleAlgorithm {

    private Instance inst;
    private List<Operation> setA;
    private int initialTime = 0;


    @Override
    public List<ResultTask> run(Instance data) throws AlgorithmException {
        ArrayList<ResultTask> results = new ArrayList<ResultTask>();
        this.inst = data;


        setA = new ArrayList<Operation>();
        getInitialASet();

        while (!setA.isEmpty()){

            // Determina la operación con menor tiempo de fin (C)
            Operation menorTiempoFin = setA.get(0);
            for (Operation op: setA) {
                if (op.getEndTime() < menorTiempoFin.getEndTime()){
                    menorTiempoFin = op;
                }
            }

            int c = menorTiempoFin.getEndTime();

            Machine m = inst.getMachines().get(menorTiempoFin.getMachineNumber());

            //System.out.println(c + " " + menorTiempoFin.getMachineNumber() + " " + m.getMachineNumber());


            setA.clear();
        }



        scheduleTask();

        //System.out.println("reached here successfully");
        
        return null;
    }

    private void getInitialASet(){
        setA.clear();

        // coger la primera operación SIN PLANIFICAR de cada Job
        for (Job j: inst.getJobs()) {
            for (Operation op: j.getOperations()) {
                if(!op.isScheduled()){
                    setA.add(op);
                    break;
                }
            }
        }

        System.out.println("SET A: " + setA.toString());

//        int totalpt = 0;
//        for (Job j: inst.getJobs()) {
//            for (Operation op: j.getOperations()) {
//                totalpt += op.getProcessingTime();
//                }
//            }

//        System.out.println(totalpt);

    }

    private void scheduleTask(){
        for (Machine m: inst.getMachines()) {
            System.out.println(m.getMachineNumber() + "\t" + m.isBusy());
        }
    }
}
