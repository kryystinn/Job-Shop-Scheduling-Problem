package application;

import application.util.console.Console;
import logic.exceptions.AlgorithmException;
import logic.exceptions.InputException;
import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.Job;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.ExtendedFileImpl;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.ATCRule;
import logic.schedule.rules.impl.EDDRule;
import logic.schedule.rules.impl.LPTRule;
import logic.schedule.rules.impl.SPTRule;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuAllRules {

    private static String outputName;
    private static Instance ins;
    private static double kValue;
    private static FileData<Instance> service;


    public static void main(String[] args) throws AlgorithmException {

        // 1. Preguntar qué tipo de instancia: Taillard o extendida.
        int instanceType = Console.readInt("""

                Welcome. What type of instance will you want to load?
                0 - Exit
                1 - Taillard
                2 - Extended (with weights and due dates)
                Please, type only the number""");

        if (instanceType == 0 || instanceType > 2 || instanceType < 0) {
            System.exit(0);
        }

        // 2. Preguntar si es solo un archivo o una carpeta con varios,
        String input = Console.readString("""

                Please, load a Taillard file with txt extension or a directory with allthe instances you are willing to 
                execute. Consider that the files must be of the same type.\s
                Example: <C:\\Users\\christine\\Downloads\\fileExample.txt> (without the <> symbols)""");

        try {
            File file = new File(input);
            int r = chooseRule(instanceType);
            if (r == 5) {
                kValue = Console.readDouble("Specify k value [0, 1]: ");
                if (kValue < 0 || kValue > 1)
                    throw new InputException("Input k must be between 0 and 1, and a double value.");
            }

            String filePath;
            if (file.isDirectory()) {
                File[] filesInFolder = file.listFiles();

                assert filesInFolder != null;
                for (File f : filesInFolder) {
                    if (f.isFile()) {
                        Path path = Paths.get(f.getPath());
                        filePath = new File(String.valueOf(path)).getPath();

                        String fileName = path.getFileName().toString();
                        String sheetName = fileName.substring(0, fileName.lastIndexOf('.'));
                        outputName = file.getName() + " " + r;

                        ins = service.getData(filePath);
                        execute(path.getParent().toString(), sheetName, r);
                        for (Job j : ins.getJobs()) {
                            j.resetOperations();
                        }
                    }
                }

            } else {
                if (file.isFile()) {
                    Path path = Paths.get(input);
                    filePath = new File(String.valueOf(path)).getPath();

                    String fileName = path.getFileName().toString();
                    String sheetName = fileName.substring(0, fileName.lastIndexOf('.'));
                    outputName = sheetName + " " + r;

                    ins = service.getData(filePath);
                    execute(path.getParent().toString(), sheetName, r);
                    for (Job j : ins.getJobs()) {
                        j.resetOperations();
                    }
                }
            }

            System.out.println("\nArchivo xlsx generado con los resultados.\n");

        } catch (Exception e) {
            throw new AlgorithmException(e);
        }

    }

    private static int chooseRule(int instanceType) throws ParserException {
        int rule = 0;
        try {
            switch (instanceType) {
                case 1:
                    rule = Console.readInt("\nWhat rule are you willing to apply?\n" +
                            "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)");
                    if (rule == 0 || rule > 2 || rule < 0) {
                        System.exit(0);
                    }
                    service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
                    break;

                case 2:
                    rule = Console.readInt("\nWhat rule are you willing to apply?\n" +
                            "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)" +
                            "\n3 - EDD (Earliest Due Date)" +
                            "\n4 - ATC (Apparent Tardiness Cost)\nPlease, type only the number");
                    if (rule == 0 || rule > 4 || rule < 0) {
                        System.exit(0);
                    }
                    service = new FileDataImpl<TaillardInstance>(new ExtendedFileImpl());
                    break;
            }

        } catch (Exception e) {
            throw new ParserException("Problem related to file instance.\n" +
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
                ruleToApply = new EDDRule(ins);
                break;

            case 4:
                ruleToApply = new ATCRule(ins, kValue);
                break;
        }


        try {
            ScheduleInstance scheduler = new ScheduleInstance(new GTAlgorithm(ins, ruleToApply));
            scheduler.executeAlgorithm();
            scheduler.generateOutput(path, outputName, sheetName);

        } catch (Exception e) {
            throw new AlgorithmException("Error in scheduling algorithm.");
        }
    }

}

