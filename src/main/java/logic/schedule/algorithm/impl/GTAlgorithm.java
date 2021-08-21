package logic.schedule.algorithm.impl;

import logic.exceptions.AlgorithmException;
import logic.graph.ConstraintGraph;
import logic.instances.*;
import logic.schedule.algorithm.ScheduleAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.SPTRule;

import java.util.*;

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

        // Inicializar el set A
        initializeASet();

        while (!setA.isEmpty()) {

            // Determinar operación con menor tiempo de fin (operación prima)
            Operation oPrime = getOPrimeOperation();
            // Construir el conjunto B
            initializeBSet(oPrime);
            // Elegir mediante una regla de prioridad la tarea a planificar del conjunto B
            Operation oStar = getOStarOperation();


            // Planificar la tarea
            // TODO: se puede hacer con IN_EDGES mirando el mayor de las operaciones relacionadas

            // Coge el valor de tiempo en el que podrá comenzar la operación seleccionada
            long newInitialTime = this.getLastEndTimeScheduled(oStar);
            // Se planifica: se cambia el tiempo inicial de la operación y se pone isScheduled a true
            oStar.scheduleOperation(newInitialTime);
            // Se añade a la lista de operaciones planificadas
            this.addResult(oStar);

            // Se modifica el tiempo de inicio de todas aquellas operaciones no planificadas que vayan a continuación
            // de la tarea que se acaba de planificar (es decir, aquellas que compartan máquina o que sean sucesoras
            // de la misma.
            for (Operation operation : constraintGraph.getOutEdges(oStar)) {
                if(!operation.isScheduled() && operation.getStartingTime() < oStar.getEndTime()) {
                    operation.setStartingTime(oStar.getEndTime());
                }
            }

            // Se elimina del conjunto A la tarea recién planificada
            setA.remove(oStar);

            // Se añade al conjunto A la operación sucesora, en caso de que exista
            for (Operation op: constraintGraph.getOutEdges(oStar)) {
                if (op.getJobNumber() == oStar.getJobNumber()){
                    setA = new ArrayList<Operation>(setA);
                    setA.add(op);
                }
            }
        }

        for (ResultTask rt: results)
            System.out.println(rt.toString());

        System.out.println(calculateLowBound());

        return results;
    }

    /**
     * Inicialización del conjunto A con las primeras operaciones sin planificar de cada trabajo
     */
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

    /**
     * Selecciona la operación del conjunto A con menor tiempo de fin
     * @return la operación con menor tiempo de fin, que se denominará oPrime
     */
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

        return opConMenorTiempoFin;
    }

    /**
     * Selecciona del conjunto A aquellas operaciones que puedan comenzar antes que o prima y que requieran la misma máquina que ella. Como mínimo, el conjunto B tendrá la operación o prima.
     * @param oPrime, la operación con menor tiempo de fin del conjunto A
     */
    private void initializeBSet(Operation oPrime) {
        setB = new ArrayList<Operation>();

        int nMachine = oPrime.getMachineNumber();
        long endTime = oPrime.getEndTime();

        for(Operation op : setA) {
            if(op.getMachineNumber() == nMachine && op.getStartingTime() < endTime) {
                setB.add(op);
            }
        }
    }

    /**
     * Elige la operación a planificar del conjunto B en función de la regla de prioridad deseada
     * @return la operación elegida a planificar
     */
    private Operation getOStarOperation() {
        return rule.run(setB);
    }

    /**
     * Recorre la lista de tareas planificadas y comprueba el tiempo de fin de aquellas que coincidan con la máquina de
     * la tarea a planificar o que sean del mismo trabajo que esta. Se queda con el mayor valor, que será
     * aquel a partir del cual la operación a planificar (o Star) podrá comenzar
     *
     * @param op a planificar
     * @return el valor de tiempo a partir del cual puede comenzar la operación
     */
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
        ResultTask result = new ResultTask(op.getProcessingTime(), op.getStartingTime(), op.getEndTime(), op.getMachineNumber(), op.getJobNumber());
        results.add(result);
    }

    private long calculateLowBound(){
        Map<Integer, Long> map = new HashMap<Integer, Long>();
        for (ResultTask rt: results) {
            long sum = 0;
            if(!map.containsKey(rt.getnMachine())) {
                map.put(rt.getnMachine(), rt.getProcessingTime());
            }
            sum = map.get(rt.getnMachine()) + rt.getProcessingTime();
            map.put(rt.getnMachine(), sum);
        }

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }


        Long minValue = map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();


        return minValue;
    }

}
