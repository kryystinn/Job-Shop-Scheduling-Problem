package application;

import logic.instances.Instance;
import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;
import logic.schedule.ScheduleInstance;
import logic.schedule.algorithm.impl.GTAlgorithm;

public class Menu {

    private static String fileName;
    private static FileData<TaillardInstance> service;
    private static ScheduleInstance scheduler;


    public static void main(String args[]){
//        fileName = Console.readString("Please, load a Taillard file with txt extension");
        fileName = "C:\\Users\\christine\\Desktop\\taillard\\tai15x15-1.txt";
        try {
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            TaillardInstance ins = service.getData(fileName);
            scheduler = new ScheduleInstance(new GTAlgorithm());
            scheduler.executeAlgorithm(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
