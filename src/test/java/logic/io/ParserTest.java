package logic.io;

import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.impl.SPTRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    private static String res;
    private static File instance;
    private static String filePath;
    private static FileData<Instance> service;
    private static ScheduleInstance scheduler;
    private static Instance ins;

    @BeforeAll
    public static void setUp() {
        res = "src\\test\\resources\\";
        service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
    }

    @Test
    public void testSinExtension() {
        instance = new File(res + "sinExtension");
        String full = instance.getAbsolutePath();
        filePath = String.valueOf(new File(full));
        Assertions.assertThrows(ParserException.class, () -> {
            ins = service.getData(filePath);
        });
    }

    @Test
    public void testNull() {
        filePath = null;
        Exception exp = Assertions.assertThrows(ParserException.class, () -> {
            ins = service.getData(filePath);
        });
        Assertions.assertTrue(exp.getMessage().contains("File is null"));
    }

    @Test
    public void testNotTxtExtension() {
        instance = new File(res + "otraExtension.pdf");
        String full = instance.getAbsolutePath();
        filePath = String.valueOf(new File(full));
        Assertions.assertThrows(ParserException.class, () -> {
            ins = service.getData(filePath);
        });
    }

    @Test
    public void testFileNotFound() {
        instance = new File(res + "invent.txt");
        String full = instance.getAbsolutePath();
        filePath = String.valueOf(new File(full));
        Assertions.assertThrows(ParserException.class, () -> {
            ins = service.getData(filePath);
        });
    }

    @Test
    public void testNotOk() {
        instance = new File(res + "tai02x03mal.txt");
        String full = instance.getAbsolutePath();
        filePath = String.valueOf(new File(full));
        Assertions.assertThrows(ParserException.class, () -> {
            ins = service.getData(filePath);
        });
    }

    @Test
    public void emptyDir() {
        File file = new File(res + "emptyDir");

        try {
            if (file.isDirectory()) {
                File[] filesInFolder = file.listFiles();
                for (int n = 0; n < filesInFolder.length; n++) {
                    File f = filesInFolder[n];

                    if (f.isFile()) {
                        Path path = Paths.get(f.getPath());
                        filePath = new File(String.valueOf(path)).getPath();
                        ins = service.getData(filePath);
                        scheduler = new ScheduleInstance(new GTAlgorithm(ins, new SPTRule()));
                    }

                }
            }
        } catch (Exception e) {
            fail();
        }
        Assertions.assertFalse(new File(file.getPath() + "\\out").exists());
    }

    @Test
    public void notEmptyDir() {
        File file = new File(res + "dir");

        try {
            if (file.isDirectory()) {
                File[] filesInFolder = file.listFiles();
                for (int n = 0; n < filesInFolder.length; n++) {
                    File f = filesInFolder[n];

                    if (f.isFile()) {
                        Path path = Paths.get(f.getPath());
                        filePath = new File(String.valueOf(path)).getPath();
                        ins = service.getData(filePath);
                        scheduler = new ScheduleInstance(new GTAlgorithm(ins, new SPTRule()));
                    }

                }
            }
        } catch (Exception e) {
            fail();
        }
        Assertions.assertFalse(new File(file.getPath() + "\\out").exists());
    }


}
