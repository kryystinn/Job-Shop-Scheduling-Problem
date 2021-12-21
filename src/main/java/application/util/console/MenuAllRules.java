package application.util.console;

import application.Menu;
import logic.exceptions.AlgorithmException;
import logic.exceptions.InputException;
import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.output.Writer;
import logic.output.impl.ExcelWriterImpl;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardExtendedFileImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuAllRules {

    private static String input;
    private static String filePath;
    private static String outputName;
    private static Instance ins;
    private static double kValue;
    private static FileData<Instance> service;
    private static ScheduleInstance scheduler;


    private static Writer w = new ExcelWriterImpl();

    public static void main(String args[]) throws ParserException {

        w.writeAllSameSheet("C:\\Users\\christine\\Downloads", "prueba", "a", 2, 33, 1, true);
        System.exit(0);

        int instanceType = Console.readInt("\nWelcome. What type of instance will you want to load?" +
                "\n0 - Exit\n1 - Taillard\n2 - Extended (with weights and due dates)" +
                "\nPlease, type only the number");

        if (instanceType == 0 || instanceType > 2 || instanceType < 0) {
            System.exit(0);
        }

        String name = Console.readString("\nPlease, load a Taillard file with txt extension or a directory with" +
                "all the instances you are willing to execute. Consider that the files must be of the same type. " +
                "\nExample: <C:\\Users\\christine\\Downloads\\fileExample.txt> (without the <> symbols)");

        input = MenuAllRules.class.getResource(name).getPath();

        File file = new File(input);

        if (file.isDirectory()) {
            File[] filesInFolder = file.listFiles();
            int rowNum = 2;
            for (File f : filesInFolder) {

                if (f.isFile()) {
                    Path path = Paths.get(f.getPath());
                    filePath = new File(String.valueOf(path)).getPath();

                    String fileName = path.getFileName().toString();
                    String instName = fileName.substring(0, fileName.lastIndexOf('.'));
                    outputName = file.getName() + " " + "try";

                    ins = service.getData(filePath);
                    //execute(path.getParent().toString(), sheetName, r);

                    rowNum++;
                }
            }

        } else {

            if (file.isFile()){
                Path path = Paths.get(input);
                filePath = new File(String.valueOf(path)).getPath();

                String fileName = path.getFileName().toString();
                String sheetName = fileName.substring(0, fileName.lastIndexOf('.'));
               // outputName = sheetName + " " + r;

                ins = service.getData(filePath);
               // execute(path.getParent().toString(), sheetName, r);
            }
        }

        System.out.println("\nArchivo xlsx generado con los resultados.\n");


    }

    private static int chooseRule(int instanceType) throws AlgorithmException {
        int rule = 0;
        try {
            switch (instanceType) {
                case 1:
                    rule = Console.readInt("\nWhat rule are you willing to apply?\n" +
                            "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)" +
                            "\n3 - MCM (Minimum Completion Time)\nPlease, type only the number");
                    if (rule == 0 || rule > 3 || rule < 0) {
                        System.exit(0);
                    }
                    service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
                    break;

                case 2:
                    rule = Console.readInt("\nWhat rule are you willing to apply?\n" +
                            "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)" +
                            "\n3 - MCM (Minimum Completion Time)\n4 - EDD (Earliest Due Date)" +
                            "\n5 - ATC (Apparent Tardiness Cost)\nPlease, type only the number");
                    if (rule == 0 || rule > 6 || rule < 0) {
                        System.exit(0);
                    }
                    service = new FileDataImpl<TaillardInstance>(new TaillardExtendedFileImpl());
                    break;
            }

        } catch (Exception e) {
            throw new AlgorithmException("Problem related to file instance.\n" +
                    "Maybe the file instance does not match the instance type selected.");
        }

        return rule;
    }

    private static void execute(String path, String sheetName, int rule) throws InputException,
            AlgorithmException {
        Rule ruleToApply = null;

        switch (rule) {
            case 1:
                ruleToApply = new SPTRule();
                break;

            case 2:
                ruleToApply = new LPTRule();
                break;

            case 3:
                ruleToApply = new MCMRule();
                break;

            case 4:
                ruleToApply = new EDDRule(ins);
                break;

            case 5:
                ruleToApply = new ATCRule(ins, kValue);
                break;
        }


        try {
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, ruleToApply));
            scheduler.executeAlgorithm();
            scheduler.generateOutput(path, outputName, sheetName);
        } catch (Exception e) {
            throw new AlgorithmException("Error in scheduling algorithm.");
        }
    }
}
