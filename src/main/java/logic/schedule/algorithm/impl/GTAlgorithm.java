package logic.schedule.algorithm.impl;

import logic.exceptions.AlgorithmException;
import logic.graph.ConstraintGraph;
import logic.instances.*;
import logic.output.Writer;
import logic.output.impl.CsvWriterImpl;
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
    private Writer writer;

    public GTAlgorithm(Instance instance) throws AlgorithmException {
        this(instance, new SPTRule());
    }

    public GTAlgorithm(Instance instance, Rule rule) throws AlgorithmException {
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
                if(!operation.isScheduled() && operation.getStartTime() < oStar.getEndTime()) {
                    operation.setStartTime(oStar.getEndTime());
                }
            }

            // Se elimina del conjunto A la tarea recién planificada
            setA.remove(oStar);

            // Se añade al conjunto A la operación sucesora, en caso de que exista
            for (Operation op: constraintGraph.getOutEdges(oStar)) {
                if (op.getJobId() == oStar.getJobId()){
                    setA = new ArrayList<Operation>(setA);
                    setA.add(op);
                }
            }
        }

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
            if(op.getMachineNumber() == nMachine && op.getStartTime() < endTime) {
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
     * Recorre la lista de tareas que tienen una arista hacia la tarea a planificar, es decir, si tienen la misma
     * máquina o bien es la tarea precedente al mismo (del mismo trabajo),
     * comprueba que se traten de tareas planificadas y comprueba los tiempos de fin de cada una de ellas.
     * Se queda con el mayor valor, que será aquel a partir del cual la operación a planificar (o Star) podrá comenzar.
     *
     * @param op a planificar
     * @return el valor de tiempo a partir del cual puede comenzar la operación
     */
    private long getLastEndTimeScheduled(Operation op) {
        long lastEndTime = 0;
        for(Operation scheduledOp : constraintGraph.getInEdges(op)) {
                if(scheduledOp.isScheduled() && scheduledOp.getEndTime() > lastEndTime) {
                    lastEndTime = scheduledOp.getEndTime();
                }

        }
        return lastEndTime;
    }

    private void addResult(Operation op) {
        ResultTask result = new ResultTask(op.getProcessingTime(), op.getStartTime(), op.getEndTime(), op.getMachineNumber(), op.getJobId());
        results.add(result);
    }

    @Override
    public void writeOutput(String name) {
        List<String[]> scheduledJobs = new ArrayList<String[]>();

        for (int i = 1; i <= inst.getnJobs(); i++) {
            String[] job = new String[inst.getnMachines()];
            int count = 0;

            for (ResultTask rt: results) {
                if (i == rt.getJobId()) {
                    job[count] = rt.toStringStartTimes();
                    count++;
                }
            }

            scheduledJobs.add(job);
        }

        for (String[] j: scheduledJobs){
            for (int i = 0; i < j.length; i++)
                System.out.print(j[i] + " ");
            System.out.println();
        }

        writer = new CsvWriterImpl();
        writer.write(scheduledJobs, name);
        System.out.println("\nArchivo cvs generado con los resultados.");
    }

}
