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
        fileName = "C:\\Users\\christine\\Desktop\\taillard\\tai02x06-1.txt";
//        fileName = Menu.class.getClassLoader().getResource("tai02x06-2.txt").getPath();
        try {
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            Instance ins = service.getData(fileName);
            scheduler = new ScheduleInstance(new GTAlgorithm(ins));
            scheduler.executeAlgorithm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
