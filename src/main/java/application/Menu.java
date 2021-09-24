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

        if (instanceType == 0 || instanceType > 2) {
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


        switch (instanceType) {
            case 1:
                service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
                break;

            case 2:
                service = new FileDataImpl<TaillardInstance>(new TaillardExtendedFileImpl());
                break;
        }

        try {
            Instance ins = service.getData(filePath);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins));
            scheduler.executeAlgorithm();
        } catch (Exception e) {
            throw new AlgorithmException("The file instance does not match the instance type selected.");
        }
    }
}
