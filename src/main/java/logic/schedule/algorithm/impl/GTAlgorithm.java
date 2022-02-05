package logic.schedule.algorithm.impl;

import application.util.console.CopyObject;
import logic.exceptions.AlgorithmException;
import logic.graph.ConstraintGraph;
import logic.instances.*;
import logic.output.Writer;
import logic.output.impl.ExcelWriterImpl;
import logic.schedule.algorithm.ScheduleAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.SPTRule;
import java.util.*;

/**
 * Clase GTAlgorithm que representa el algoritmo de Giffler y Thompson.
 *
 */
public class GTAlgorithm implements ScheduleAlgorithm {

    private final Instance inst;
    private final Rule rule;
    private final ConstraintGraph constraintGraph;
    private List<Operation> setA;
    private List<Operation> setB;
    private List<ResultTask> results;
    private Writer writer;

    /**
     * Constructor de la clase {@link GTAlgorithm} con la regla SPT por defecto.
     *
     * @param instance instancia a planificar
     * @throws AlgorithmException si falla al ejecutar el algoritmo
     */
    public GTAlgorithm(Instance instance) throws AlgorithmException {
        this(instance, new SPTRule());
    }

    /**
     * Constructor de la clase {@link GTAlgorithm}.
     *
     * @param instance instancia a planificar
     * @param rule regla de prioridad para guiar al algoritmo
     * @throws AlgorithmException si falla al ejecutar el algoritmo
     */
    public GTAlgorithm(Instance instance, Rule rule) throws AlgorithmException {
        this.inst = CopyObject.copy(instance);
        this.rule = CopyObject.copy(rule);
        this.constraintGraph = new ConstraintGraph(this.inst);
    }

    /**
     * Método que ejecuta el algoritmo G&T.
     *
     * @return listado de tareas planificadas
     */
    @Override
    public List<ResultTask> run() {

        results = new ArrayList<>();

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

            // Coge el valor de tiempo en el que podrá comenzar la operación seleccionada
            long newInitialTime = this.getLastEndTimeScheduled(oStar);

            // Se planifica:
            // - se cambia el tiempo inicial de la operación
            // - se pone isScheduled a true
            oStar.scheduleOperation(newInitialTime);

            // Se actualiza el machine release time de la máquina en la que se procesó:
            //inst.getJobs().get(oStar.getJobId()-1).getOperations().get(oStar.getOperationNumber()-1)
            // .setMachineReleaseTime(oStar.getEndTime());
            //inst.getMachines().get(oStar.getMachineNumber()-1).setReleaseTime(oStar.getEndTime());

            // Se añade a la lista de operaciones planificadas
            this.addResult(oStar);

            // Se modifica el tiempo de inicio de todas aquellas operaciones no planificadas que vayan a continuación
            // de la tarea que se acaba de planificar (es decir, aquellas que compartan máquina o que sean sucesoras
            // de la misma.
            for (Operation operation : constraintGraph.getOutEdges(oStar)) {
                if(!operation.isScheduled()) {
                    if (operation.getStartTime() < oStar.getEndTime())
                        operation.setStartTime(oStar.getEndTime());
                }
            }

            // Se elimina del conjunto A la tarea recién planificada
            setA.remove(oStar);

            // Se añade al conjunto A la operación sucesora, en caso de que exista
            for (Operation op: constraintGraph.getOutEdges(oStar)) {
                if (op.getJobId() == oStar.getJobId()){
                    setA = new ArrayList<>(setA);
                    setA.add(op);
                }
            }
        }

        return results;
    }

    /**
     * Inicialización del conjunto A con las primeras operaciones sin planificar de cada trabajo
     *
     */
    private void initializeASet(){
        setA = new ArrayList<>();

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
     * Selecciona la operación del conjunto A con menor tiempo de fin.
     *
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
     * Selecciona del conjunto A aquellas operaciones que puedan comenzar antes que o prima y que requieran la misma
     * máquina que ella. Como mínimo, el conjunto B tendrá la operación o prima.
     *
     * @param oPrime, la operación con menor tiempo de fin del conjunto A
     */
    private void initializeBSet(Operation oPrime) {
        setB = new ArrayList<>();

        if (oPrime.getProcessingTime() == 0)
            setB.add(oPrime);

        int nMachine = oPrime.getMachineId();
        long endTime = oPrime.getEndTime();

        for(Operation op : setA) {
            if(op.getMachineId() == nMachine && op.getStartTime() < endTime) {
                setB.add(op);
            }
        }
    }

    /**
     * Elige la operación a planificar del conjunto B en función de la regla de prioridad deseada.
     *
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
     * @param op operación a planificar
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

    /**
     * Crea una nueva tarea resuelta y la añade a la lista de tareas planificadas {@link #results}.
     *
     * @param op operación planificada a añadir en la lista
     */
    private void addResult(Operation op) {
        ResultTask result = new ResultTask(op.getProcessingTime(), op.getStartTime(), op.getEndTime(),
                op.getMachineId(), op.getJobId());
        results.add(result);
    }

    /**
     * Obtiene los datos de los startTimes de las tareas planificadas y prepara una lista de String[] en la que
     * cada array será un trabajo con los datos de cada operación del mismo.
     *
     * @param path ruta en la que guardar el fichero
     * @param outputName nombre de salida del fichero
     * @param name nombre de la hoja de cálculo
     */
    @Override
    public void writeStartingTimeMatrix(String path, String outputName, String name) {
        List<String[]> scheduledJobs = new ArrayList<>();

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

        writer = new ExcelWriterImpl();
        writer.writeMatrixStartTimes(path, outputName, name, scheduledJobs);
    }

    /**
     * En función de la función objetivo a analizar obtiene bien el makespan o el tardiness de las instancias
     * planificadas. Después de esto, los imprime en el fichero Excel.
     *
     * @param path ruta en la que guardar el fichero
     * @param output nombre de salida del fichero
     * @param instName nombre de la instancia planificada
     * @param rowNum número de fila (número de instancia dentro del directorio, en caso de haberlo)
     * @param colNum número de columna (número de regla ejecutada)
     * @param extended true/false si la instancia es extendida o no, respectivamente
     * @param objFunc función objetivo a analizar
     */
    @Override
    public void writeAll(String path, String output, String instName, int rowNum, int colNum,
                         boolean extended, String objFunc) {

        long result = -1;

        if (objFunc.equals("m")) {
            result = results.get(results.size() - 1).getEndTime();

        } else if (objFunc.equals("t")) {
            long tardiness = 0;
            for (Job j: inst.getJobs()) {
                Operation last = j.getOperations().get(j.getOperations().size()-1);
                long completionTime = last.getEndTime();
                long dueDate = j.getDueDate();
                tardiness += Math.max(0, completionTime - dueDate);
            }
            result = tardiness;
        }

        writer = new ExcelWriterImpl();
        writer.write(path, output, instName, rowNum, colNum, inst,
                result, extended, objFunc);
    }

}
