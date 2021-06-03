package logic.schedule.algorithm.impl;

import logic.exceptions.AlgorithmException;
import logic.graph.ConstraintGraph;
import logic.instances.*;
import logic.instances.taillard.TaillardInstance;
import logic.schedule.algorithm.ScheduleAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.SPTRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GTAlgorithm implements ScheduleAlgorithm {

    private Instance inst;
    private Rule rule;
    private ConstraintGraph constraintGraph;

    private List<Operation> setA;
    private List<Operation> setB;

    private List<ResultTask> results;

    public GTAlgorithm(Instance instance) {
        this(instance, new SPTRule());
    }

    public GTAlgorithm(Instance instance, Rule rule) {
        this.inst = instance;
        this.rule = rule;
        this.constraintGraph = new ConstraintGraph(instance);
    }

    @Override
    public List<ResultTask> run() throws AlgorithmException {
        results = new ArrayList<ResultTask>();
        initializeASet();

        while (!setA.isEmpty()){
            Operation oPrime = getOPrimeOperation();
            initializeBSet(oPrime);
            Operation oStar = getOStarOperation();

            //System.out.println(oStar);

            // Planificar
            // TODO: se puede hacer con IN_EDGES mirando el mayor de las operaciones relacionadas
            long newInitialTime = this.getLastEndTimeScheduled(oStar);
            oStar.scheduleOperation(newInitialTime);
            this.addResult(oStar);

            for (Operation operation : constraintGraph.getOutEdges(oStar)) {
                if(!operation.isScheduled() && operation.getInitialTime() < oStar.getEndTime()) {
                    operation.setInitialTime(oStar.getEndTime());
                }
            }

            setA.remove(oStar);
            Optional<Operation> nextOperation = constraintGraph.getOutEdges(oStar).stream().filter(x -> x.getJobNumber() == oStar.getJobNumber()).findFirst();
            if(nextOperation.isPresent()) {
                setA = new ArrayList<Operation>(setA);
                setA.add(nextOperation.get());
            }
        }

        for (ResultTask rt: results)
            System.out.println(rt.toString());

        return results;
    }

    private void initializeASet(){
        setA = new ArrayList<Operation>();

        // coger la primera operación SIN PLANIFICAR de cada Job
        for (Job j: inst.getJobs()) {
            for (Operation op: j.getOperations()) {
                if(!op.isScheduled()){
                    setA.add(op);
                    break;
                }
            }
        }
    }

    private Operation getOPrimeOperation() {
        if (setA.isEmpty())
            return null;
        // Determina la operación con menor tiempo de fin (C)
        Operation opConMenorTiempoFin = setA.get(0);
        for (Operation op: setA) {
            if (op.getEndTime() < opConMenorTiempoFin.getEndTime()){
                opConMenorTiempoFin = op;
            }
        }
        //System.out.println(opConMenorTiempoFin.getInitialTime() + " " + opConMenorTiempoFin.getProcessingTime() + " " + opConMenorTiempoFin.getEndTime());
        return opConMenorTiempoFin;
    }

    private void initializeBSet(Operation oPrime) {
        setB = new ArrayList<Operation>();

        int nMachine = oPrime.getMachineNumber();
        long endTime = oPrime.getEndTime();

        for(Operation op : setA) {
            if(op.getMachineNumber() == nMachine && op.getInitialTime() < endTime) {
                setB.add(op);
            }
        }
    }

    private Operation getOStarOperation() {
        return rule.run(setB);
    }

    private long getLastEndTimeScheduled(Operation op) {
        long lastEndTime = 0;
        for(ResultTask scheduledRt : results) {
            if(scheduledRt.getnMachine() == op.getMachineNumber() || scheduledRt.getnJob() == op.getJobNumber()) {
                if(scheduledRt.getEndTime() > lastEndTime) {
                    lastEndTime = scheduledRt.getEndTime();
                }
            }
        }
        return lastEndTime;
    }

    private void addResult(Operation op) {
        ResultTask result = new ResultTask(op.getProcessingTime(), op.getInitialTime(), op.getEndTime(), op.getMachineNumber(), op.getJobNumber());
        results.add(result);
    }
}
