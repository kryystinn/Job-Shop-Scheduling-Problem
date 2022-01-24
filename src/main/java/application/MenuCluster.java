package application;

import application.util.console.Console;
import logic.exceptions.AlgorithmException;
import logic.exceptions.ParserException;
import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.ExtendedFileImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;
import logic.schedule.rules.impl.*;

import java.io.File;
import java.net.URL;

public class MenuCluster {

    private static String fileName;
    private static String filePath;
    private static FileData<Instance> service;
    private static ScheduleInstance scheduler;


    public static void main(String args[]) throws ParserException, AlgorithmException {

        String input = Console.readString();

        fileName = Console.readString();
        URL path = Menu.class.getResource(fileName);
        filePath = new File(path.getFile()).getPath();
        service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());

        service = new FileDataImpl<TaillardInstance>(new ExtendedFileImpl());


        Instance ins = service.getData(filePath);

        scheduler = new ScheduleInstance(new GTAlgorithm(ins, new SPTRule()));
        scheduler.executeAlgorithm();



    }

}
