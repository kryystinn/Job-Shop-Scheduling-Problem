package logic.schedule;
import logic.instances.Instance;
import logic.instances.ResultTask;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.LPTRule;
import logic.schedule.rules.impl.MCTRule;
import logic.schedule.rules.impl.SPTRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class RulesTest {

    private static String res;
    private static File instance;
    private static String filePath;
    private static FileData<Instance> service;
    private static Instance ins;
    private static ScheduleInstance scheduler;
    private static List<ResultTask> results;

    @BeforeAll
    public static void setUp() {
        res = "src\\test\\resources\\";
        service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
        results = new ArrayList<>();
    }

    @Test
    public void testSPTRule() {
        Rule rule = new SPTRule();

        try {
            instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            filePath = String.valueOf(new File(full));
            ins = service.getData(filePath);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            results = scheduler.executeAlgorithm();

        } catch (Exception e) {
            fail();
        }
        // Comprobar que los resultados se componen de las 6 operaciones de la instancia
        Assertions.assertEquals(results.size(), 6);

        Assertions.assertEquals(results.get(0).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(0).getJobId(), 2);
        Assertions.assertEquals(results.get(0).getnMachine(), 3);
        Assertions.assertEquals(results.get(0).getEndTime(), 2);

        Assertions.assertEquals(results.get(1).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(1).getJobId(), 2);
        Assertions.assertEquals(results.get(1).getnMachine(), 1);
        Assertions.assertEquals(results.get(1).getEndTime(), 4);

        Assertions.assertEquals(results.get(2).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(2).getJobId(), 2);
        Assertions.assertEquals(results.get(2).getnMachine(), 2);
        Assertions.assertEquals(results.get(2).getEndTime(), 6);

        Assertions.assertEquals(results.get(3).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(3).getJobId(), 1);
        Assertions.assertEquals(results.get(3).getnMachine(), 1);
        Assertions.assertEquals(results.get(3).getEndTime(), 7);

        Assertions.assertEquals(results.get(4).getProcessingTime(), 1);
        Assertions.assertEquals(results.get(4).getJobId(), 1);
        Assertions.assertEquals(results.get(4).getnMachine(), 2);
        Assertions.assertEquals(results.get(4).getEndTime(), 8);

        Assertions.assertEquals(results.get(5).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(5).getJobId(), 1);
        Assertions.assertEquals(results.get(5).getnMachine(), 3);
        Assertions.assertEquals(results.get(5).getEndTime(), 11);

    }

    @Test
    public void testLPTRule() {
        Rule rule = new LPTRule();

        try {
            instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            filePath = String.valueOf(new File(full));
            ins = service.getData(filePath);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            results = scheduler.executeAlgorithm();

        } catch (Exception e) {
            fail();
        }
        // Comprobar que los resultados se componen de las 6 operaciones de la instancia
        Assertions.assertEquals(results.size(), 6);

        Assertions.assertEquals(results.get(0).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(0).getJobId(), 2);
        Assertions.assertEquals(results.get(0).getnMachine(), 3);
        Assertions.assertEquals(results.get(0).getEndTime(), 2);

        Assertions.assertEquals(results.get(1).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(1).getJobId(), 1);
        Assertions.assertEquals(results.get(1).getnMachine(), 1);
        Assertions.assertEquals(results.get(1).getEndTime(), 3);

        Assertions.assertEquals(results.get(2).getProcessingTime(), 1);
        Assertions.assertEquals(results.get(2).getJobId(), 1);
        Assertions.assertEquals(results.get(2).getnMachine(), 2);
        Assertions.assertEquals(results.get(2).getEndTime(), 4);

        Assertions.assertEquals(results.get(3).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(3).getJobId(), 2);
        Assertions.assertEquals(results.get(3).getnMachine(), 1);
        Assertions.assertEquals(results.get(3).getEndTime(), 5);

        Assertions.assertEquals(results.get(4).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(4).getJobId(), 1);
        Assertions.assertEquals(results.get(4).getnMachine(), 3);
        Assertions.assertEquals(results.get(4).getEndTime(), 7);

        Assertions.assertEquals(results.get(5).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(5).getJobId(), 2);
        Assertions.assertEquals(results.get(5).getnMachine(), 2);
        Assertions.assertEquals(results.get(5).getEndTime(), 7);
    }

    @Test
    public void testMCTRule() {
        Rule rule = new MCTRule();

        try {
            instance = new File(res + "tai02x03.txt");
            String full = instance.getAbsolutePath();
            filePath = String.valueOf(new File(full));
            ins = service.getData(filePath);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            results = scheduler.executeAlgorithm();

        } catch (Exception e) {
            fail();
        }
        // Comprobar que los resultados se componen de las 6 operaciones de la instancia
        Assertions.assertEquals(results.size(), 6);

        Assertions.assertEquals(results.get(0).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(0).getJobId(), 2);
        Assertions.assertEquals(results.get(0).getnMachine(), 3);
        Assertions.assertEquals(results.get(0).getEndTime(), 2);

        Assertions.assertEquals(results.get(1).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(1).getJobId(), 1);
        Assertions.assertEquals(results.get(1).getnMachine(), 1);
        Assertions.assertEquals(results.get(1).getEndTime(), 3);

        Assertions.assertEquals(results.get(2).getProcessingTime(), 1);
        Assertions.assertEquals(results.get(2).getJobId(), 1);
        Assertions.assertEquals(results.get(2).getnMachine(), 2);
        Assertions.assertEquals(results.get(2).getEndTime(), 4);

        Assertions.assertEquals(results.get(3).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(3).getJobId(), 2);
        Assertions.assertEquals(results.get(3).getnMachine(), 1);
        Assertions.assertEquals(results.get(3).getEndTime(), 5);

        Assertions.assertEquals(results.get(4).getProcessingTime(), 3);
        Assertions.assertEquals(results.get(4).getJobId(), 1);
        Assertions.assertEquals(results.get(4).getnMachine(), 3);
        Assertions.assertEquals(results.get(4).getEndTime(), 7);

        Assertions.assertEquals(results.get(5).getProcessingTime(), 2);
        Assertions.assertEquals(results.get(5).getJobId(), 2);
        Assertions.assertEquals(results.get(5).getnMachine(), 2);
        Assertions.assertEquals(results.get(5).getEndTime(), 7);
    }

}
