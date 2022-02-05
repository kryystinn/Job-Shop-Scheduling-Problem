package logic.io;

import logic.instances.Instance;
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

import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    private static String res;

    @BeforeAll
    public static void setUp() {
        res = "src\\test\\resources\\";
    }

    @Test
    public void testWriteOutput() {
        Rule rule = new SPTRule();
        File f = new File(res + "/out/tai02x03 m.xlsx");
        if (f.exists())
            f.delete();

        try {
            String instName = "tai02x03";
            File instance = new File(res + instName + ".txt");
            String full = instance.getAbsolutePath();
            String filePath = String.valueOf(new File(full));
            FileData<Instance> service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            Instance ins = service.getData(filePath);
            ScheduleInstance scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            scheduler.executeAlgorithm();
            scheduler.generateAllOutput(res, instName, "tai02x03", 2, 2, false, "m");

        } catch (Exception e) {
            fail();
        }

        Assertions.assertTrue(f.exists());
    }
}
