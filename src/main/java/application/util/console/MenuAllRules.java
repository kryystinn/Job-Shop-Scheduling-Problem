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
import java.util.ArrayList;
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

    public static void main(String args[]) throws ParserException, AlgorithmException, InputException {

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
        String ext = args[2];
        if (ext.toLowerCase().equals("e"))
            extended = true;

        selectInstType(extended);

        File file = new File(input);


        if (file.isDirectory()) {
            File[] filesInFolder = file.listFiles();
            int rowNum = 2;
            boolean added = false;

            for (File f : filesInFolder) {

                if (f.isFile()) {
                    Path path = Paths.get(f.getPath());
                    filePath = new File(String.valueOf(path)).getPath();

                    String fileName = path.getFileName().toString();
                    String instName = fileName.substring(0, fileName.lastIndexOf('.'));
                    outputName = file.getName();

                    ins = service.getData(filePath);

                    if (!added){
                        if (objFunction.equals("t")) {
                            rules.add(new ATCRule(ins, 0));
                            rules.add(new ATCRule(ins, 0.25));
                            rules.add(new ATCRule(ins, 0.5));
                            rules.add(new ATCRule(ins, 1));
                            rules.add(new EDDRule(ins));
                        }
                        added = true;
                    }


                    for (int i = 0; i < rules.size(); i++) {
                        int colNum = i+2;
                        execute(path.getParent().toString(), instName, rowNum, colNum, rules.get(i), extended);
                        ins = service.getData(filePath);
                    }

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

    private static boolean selectInstType(boolean extended) throws AlgorithmException {
        rules = new ArrayList<Rule>();
        rules.add(new SPTRule());
        rules.add(new LPTRule());
        rules.add(new MCMRule());
        try {
            if (!extended)
                service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            else
                service = new FileDataImpl<TaillardInstance>(new TaillardExtendedFileImpl());

        } catch (Exception e) {
            throw new AlgorithmException("Problem related to file instance.\n" +
                    "Maybe the file instance does not match the instance type selected.");
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

