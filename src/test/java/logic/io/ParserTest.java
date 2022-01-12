package logic.io;

import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ParserTest {

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


}
