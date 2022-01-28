package logic.instances;

import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.ExtendedFileImpl;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class InstanceTest {

    private static String res;
    private static FileData<Instance> service;
    private static Instance ins;
    private static ResultTask rt;

    @BeforeAll
    public static void setUp() {
        res = "src\\test\\resources\\";
    }

    @Test
    public void testInstance() {
        try {
            File instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            String filePath = String.valueOf(new File(full));
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            ins = service.getData(filePath);

        } catch (Exception e) {
            fail();
        }

        // Comprobar los valores básicos de la instancia
        Assertions.assertFalse(ins.getJobs().isEmpty());
        Assertions.assertFalse(ins.getMachines().isEmpty());
        Assertions.assertEquals(ins.getnJobs(), 2);
        Assertions.assertEquals(ins.getnMachines(), 3);

        // Suma de todos los tiempos de procesamiento de todas las operaciones
        Assertions.assertEquals(ins.getTotalProcessingTime(), 13);
        Assertions.assertEquals(ins.getUpperBound(), 20);
        Assertions.assertEquals(ins.getLowerBound(), 0);

        // Comprobar los tiempos de procesamiento de las operaciones del primer trabajo
        Assertions.assertEquals(ins.getJobs().get(0).getJobId(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getDueDate(), 0);
        Assertions.assertEquals(ins.getJobs().get(0).getWeight(), 0);
        Assertions.assertFalse(ins.getJobs().get(0).getOperations().get(0).isScheduled());
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getOperationId(), 0);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getProcessingTime(), 3);
        Assertions.assertFalse(ins.getJobs().get(0).getOperations().get(1).isScheduled());
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getOperationId(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getProcessingTime(), 1);
        Assertions.assertFalse(ins.getJobs().get(0).getOperations().get(2).isScheduled());
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getOperationId(), 2);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getProcessingTime(), 3);

        // Comprobar las máquinas de las operaciones del primer trabajo
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getMachineId(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getMachineId(), 2);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getMachineId(), 3);

        // Comprobar los tiempos de procesamiento de las operaciones del segundo trabajo
        Assertions.assertEquals(ins.getJobs().get(1).getJobId(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getDueDate(), 0);
        Assertions.assertEquals(ins.getJobs().get(1).getWeight(), 0);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getOperationId(), 0);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getProcessingTime(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getOperationId(), 1);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getProcessingTime(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getOperationId(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getProcessingTime(), 2);

        // Comprobar las máquinas de las operaciones del segundo trabajo
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getMachineId(), 3);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getMachineId(), 1);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getMachineId(), 2);

        // Comprobar los tiempos de start time de cada operación
        // Las primeras operaciones han de tener tiempo 0:
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getStartTime(), 0);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getStartTime(), 0);

        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getStartTime(), 3);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getStartTime(), 2);

        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getStartTime(), 4);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getStartTime(), 4);

        // Máquinas
        List<Machine> m = ins.getMachines();
        Assertions.assertEquals(m.get(0).getMachineId(), 0);
        m.get(0).setReleaseTime(0);
        Assertions.assertEquals(m.get(0).getReleaseTime(), 0);

        m.get(1).setReleaseTime(5);
        Assertions.assertEquals(m.get(1).getReleaseTime(), 5);
        Assertions.assertEquals(m.get(1).getMachineId(), 1);

        m.get(2).setReleaseTime(1);
        Assertions.assertEquals(m.get(2).getReleaseTime(), 1);
        Assertions.assertEquals(m.get(2).getMachineId(), 2);

        Assertions.assertFalse(m.get(0).isBusy());
        Assertions.assertFalse(m.get(1).isBusy());
        Assertions.assertFalse(m.get(2).isBusy());

        // Simular que se planifica una tarea
        ins.getJobs().get(0).getOperations().get(0).scheduleOperation(10);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getStartTime(), 10);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getEndTime(),
                ins.getJobs().get(0).getOperations().get(0).getProcessingTime() +
                        ins.getJobs().get(0).getOperations().get(0).getStartTime());
        Assertions.assertTrue(ins.getJobs().get(0).getOperations().get(0).isScheduled());
        ins.getJobs().get(0).resetOperations();
        Assertions.assertFalse(ins.getJobs().get(0).getOperations().get(0).isScheduled());
    }

    @Test
    public void testInstanceExtended() {
        try {
            File instance = new File(res + "tai02x03ext.txt");
            String full = instance.getAbsolutePath();
            String filePath = String.valueOf(new File(full));
            service = new FileDataImpl<TaillardInstance>(new ExtendedFileImpl());
            ins = service.getData(filePath);
        } catch (Exception e) {
            fail();
        }

        // Comprobar los valores básicos de la instancia
        Assertions.assertFalse(ins.getJobs().isEmpty());
        Assertions.assertFalse(ins.getMachines().isEmpty());
        Assertions.assertEquals(ins.getnJobs(), 2);
        Assertions.assertEquals(ins.getnMachines(), 3);


        // Comprobar los tiempos de procesamiento de las operaciones del primer trabajo
        Assertions.assertEquals(ins.getJobs().get(0).getJobId(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getDueDate(), 10);
        Assertions.assertEquals(ins.getJobs().get(0).getWeight(), 4.0);

        // Comprobar los tiempos de procesamiento de las operaciones del segundo trabajo
        Assertions.assertEquals(ins.getJobs().get(1).getJobId(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getDueDate(), 7);
        Assertions.assertEquals(ins.getJobs().get(1).getWeight(), 2.0);
    }

    @Test
    public void testResultTask() {
        try {
            File instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            String filePath = String.valueOf(new File(full));
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            ins = service.getData(filePath);
            Operation o = ins.getJobs().get(0).getOperations().get(0);
            rt = new ResultTask(o.getProcessingTime(), 0, o.getProcessingTime(), o.getMachineId(),
                    o.getJobId());
        } catch (Exception e) {
            fail();
        }
        // Comprobar el funcionamiento de ResultTask:
        Assertions.assertEquals(rt.getnMachine(), ins.getJobs().get(0).getOperations().get(0).getMachineId());
        Assertions.assertEquals(rt.getStartTime(), 0);
        Assertions.assertEquals(rt.getProcessingTime(),
                ins.getJobs().get(0).getOperations().get(0).getProcessingTime());
        Assertions.assertEquals(rt.getJobId(), ins.getJobs().get(0).getOperations().get(0).getJobId());
        Assertions.assertEquals(rt.getEndTime(), ins.getJobs().get(0).getOperations().get(0).getProcessingTime());
    }
}
