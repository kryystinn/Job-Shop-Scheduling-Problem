package application;

import application.util.console.Console;
import logic.exceptions.AlgorithmException;
import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.FileParserImpl;
import logic.parser.impl.TaillardExtendedFileImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Menu {

    private static String fileName;
    private static String filePath;
    private static FileData<Instance> service;
    private static ScheduleInstance scheduler;


    public static void main(String args[]) throws ParserException, AlgorithmException {
        int instanceType = Console.readInt("\nWelcome. What type of instance will you want to load?" +
                "\n0 - Exit\n1 - Taillard\n2 - Taillard extended (with weights and due dates)" +
                "\nPlease, type only the number");

        if (instanceType == 0 || instanceType > 2 || instanceType < 0) {
            System.exit(0);
        }


        try {
            fileName = Console.readString("\nPlease, load a Taillard file with txt extension. " +
                    "Consider that the file must be in the same directory as the exe file." +
                    "\nExample: <fileExample.txt> (without the <> symbols)");
            URL path = Menu.class.getResource(fileName);
            filePath = new File(path.getFile()).getPath();
        } catch (Exception e) {
            throw new ParserException("Error related to the file path.");
        }

        int rule = 0;
        switch (instanceType) {
            case 1:
                rule = Console.readInt("\nWhat rule are you willing to apply?\nPlease, type only the number." +
                        "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)" +
                        "\n3 - MCM (Minimum Completion Time)");
                if (rule == 0 || rule > 3 || rule < 0) {
                    System.exit(0);
                }
                service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
                break;

            case 2:
                rule = Console.readInt("\nWhat rule are you willing to apply?\nPlease, type only the number." +
                        "\n1 - SPT (Shortest Processing Time)\n2 - LPT (Longest Processing Time)" +
                        "\n3 - MCM (Minimum Completion Time)\n4 - EDD (Earliest Due Date)" +
                        "\n5 - ATC (Apparent Tardiness Cost)");
                if (rule == 0 || rule > 6 || rule < 0) {
                    System.exit(0);
                }
                service = new FileDataImpl<TaillardInstance>(new TaillardExtendedFileImpl());
                break;
        }

        try {
            Instance ins = service.getData(filePath);

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
                    ruleToApply = new EDDRule(ins.getJobs());
                    break;

                case 5:
                    ruleToApply = new ATCRule(ins.getJobs());
                    break;
            }

            scheduler = new ScheduleInstance(new GTAlgorithm(ins, ruleToApply));
            scheduler.executeAlgorithm();
        } catch (Exception e) {
            throw new AlgorithmException("The file instance does not match the instance type selected.");
        }
    }
}
