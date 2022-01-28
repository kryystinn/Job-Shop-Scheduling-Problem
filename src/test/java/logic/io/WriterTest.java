package logic.io;

import logic.instances.Instance;
import logic.instances.ResultTask;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.SPTRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

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
        results = new ArrayList<>();
    }

    @Test
    public void testWriteOutput() {
        Rule rule = new SPTRule();
        File f = new File(res + "/out/tai02x03 m.xlsx");
        if (f.exists())
            f.delete();

        try {
            String instName = "tai02x03";
            instance = new File(res + instName + ".txt");
            String full = instance.getAbsolutePath();
            filePath = String.valueOf(new File(full));
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            ins = service.getData(filePath);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            scheduler.executeAlgorithm();
            scheduler.generateAllOutput(res, instName, "tai02x03", 2, 2, false, "m");

        } catch (Exception e) {
            fail();
        }

        Assertions.assertTrue(f.exists());
    }
}
