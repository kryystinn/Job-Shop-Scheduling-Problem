package logic.instances;

import logic.exceptions.ParserException;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.fail;

public class InstanceTest {

    private static String res;
    private static File instance;
    private static String filePath;
    private static FileData<Instance> service;
    private static Instance ins;

    @BeforeAll
    public static void setUp() {
        res = "src\\test\\resources\\";
        service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
    }

    @Test
    public void testInstance() {
        try {
            instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            filePath = String.valueOf(new File(full));
            ins = service.getData(filePath);
        } catch (Exception e) {
            fail();
        }

        // Comprobar los valores básicos de la instancia
        Assertions.assertTrue(!ins.getJobs().isEmpty());
        Assertions.assertTrue(!ins.getMachines().isEmpty());
        Assertions.assertTrue(ins.getnJobs() == 2);
        Assertions.assertTrue(ins.getnMachines() == 3);

        // Suma de todos los tiempos de procesamiento de todas las operaciones
        Assertions.assertEquals(ins.getTotalProcessingTime(), 13);

        // Comprobar los tiempos de procesamiento de las operaciones del primer trabajo
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getProcessingTime(), 3);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getProcessingTime(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getProcessingTime(), 3);

        // Comprobar las máquinas de las operaciones del primer trabajo
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getMachineNumber(), 1);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getMachineNumber(), 2);
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getMachineNumber(), 3);

        // Comprobar los tiempos de procesamiento de las operaciones del segundo trabajo
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getProcessingTime(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getProcessingTime(), 2);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getProcessingTime(), 2);

        // Comprobar las máquinas de las operaciones del segundo trabajo
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getMachineNumber(), 3);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getMachineNumber(), 1);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getMachineNumber(), 2);

        // Comprobar los tiempos de start time de cada operación
        // Las primeras operaciones han de tener tiempo 0:
        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(0).getStartTime(), 0);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(0).getStartTime(), 0);

        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(1).getStartTime(), 3);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(1).getStartTime(), 2);

        Assertions.assertEquals(ins.getJobs().get(0).getOperations().get(2).getStartTime(), 4);
        Assertions.assertEquals(ins.getJobs().get(1).getOperations().get(2).getStartTime(), 4);
    }

}
