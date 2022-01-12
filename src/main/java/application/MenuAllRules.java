package application;

import logic.exceptions.AlgorithmException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MenuAllRules {

    private static String input;
    private static String filePath;
    private static String outputName;
    private static Instance ins;
    private static String objFunction;
    private static List<Rule> rules;
    private static FileData<Instance> service;
    private static ScheduleInstance scheduler;


    private static Writer w = new ExcelWriterImpl();

    public static void main(String args[]) throws ParserException, AlgorithmException {

        // input example: m prueba

        objFunction = args[0];

        objFunction = String.valueOf(objFunction);

        if (!objFunction.toLowerCase().equals("m") && !objFunction.toLowerCase().equals("t")) {
            System.exit(0);
        }

        String name = args[1];
        //input = MenuAllRules.class.getResource(name).getPath();
        input = name;

        boolean extended = false;
        if (args.length > 2) {
            String ext = args[2];
            if (ext.toLowerCase().equals("e"))
                extended = true;
        }


        selectInstType(extended);
        rules = new ArrayList<Rule>();

        try {
            File file = new File(input);

            if (file.isDirectory()) {

                File[] filesInFolder = file.listFiles();
                Arrays.sort(filesInFolder, Comparator.comparingLong(File::lastModified));

                int rowNum = 2;

                for (int n = 0; n < filesInFolder.length; n++) {
                    File f = filesInFolder[n];

                    if (f.isFile()) {
                        Path path = Paths.get(f.getPath());
                        filePath = new File(String.valueOf(path)).getPath();
                        String fileName = path.getFileName().toString();
                        String instName = fileName.substring(0, fileName.lastIndexOf('.'));
                        outputName = file.getName();
                        ins = service.getData(filePath);

                        addRules();

                        for (int i = 0; i < rules.size(); i++) {
                            int colNum = i+2;
                            execute(path.getParent().toString(), instName, rowNum, colNum, rules.get(i), extended);
                        }

                        if (objFunction.equals("m") && extended) {
                            n += 2;
                        }

                        rowNum++;
                    }
                }

            } else {

                if (file.isFile()){
                    Path path = Paths.get(input);
                    filePath = new File(String.valueOf(path)).getPath();

                    String fileName = path.getFileName().toString();
                    String instName = fileName.substring(0, fileName.lastIndexOf('.'));
                    outputName = instName;
                    ins = service.getData(filePath);

                    addRules();

                    for (int i = 0; i < rules.size(); i++) {
                        int colNum = i+2;
                        execute(path.getParent().toString(), instName, 2, colNum, rules.get(i), extended);
                    }
                }
            }

            System.out.println("\nArchivo xlsx generado con los resultados.\n");

        } catch (Exception e) {
            throw new AlgorithmException(e);
        }

    }

    private static void addRules() {
        rules.clear();
        rules.add(new SPTRule());
        rules.add(new LPTRule());
        rules.add(new MCTRule());

        if (objFunction.equals("t")) {
            rules.add(new EDDRule(ins));
            rules.add(new ATCRule(ins, 0.25));
            rules.add(new ATCRule(ins, 0.5));
            rules.add(new ATCRule(ins, 0.75));
            rules.add(new ATCRule(ins, 1));
        }
    }

    private static boolean selectInstType(boolean extended) throws ParserException {

        try {
            if (extended)
                service = new FileDataImpl<TaillardInstance>(new TaillardExtendedFileImpl());
            else if (!extended && objFunction.equals("m"))
                service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());

        } catch (Exception e) {
            throw new ParserException("Problem related to file instance.\n" +
                    "Maybe the file instance cannot be executed with the objective function selected.");
        }
        return false;
    }

    private static void execute(String path, String instName, int rowNum, int colNum, Rule rule, boolean ext)
            throws AlgorithmException {
        try {
            scheduler = new ScheduleInstance(new GTAlgorithm(ins, rule));
            scheduler.executeAlgorithm();
            scheduler.generateAllOutput(path, outputName, instName, rowNum, colNum, ext, objFunction);

        } catch (Exception e) {
            throw new AlgorithmException(e);
        }
    }
}

