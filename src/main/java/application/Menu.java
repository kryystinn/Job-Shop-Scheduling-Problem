package application;

import logic.instances.taillard.TaillardInstance;
import logic.parser.FileData;
import logic.parser.impl.FileDataImpl;
import logic.parser.impl.TaillardFileImpl;

public class Menu {

    private static String fileName;
    private static FileData<TaillardInstance> service;


    public static void main(String args[]){
//        fileName = Console.readString("Please, load a Taillard file with txt extension");
        fileName = "C:\\Users\\christine\\Desktop\\taillard\\tai15x15-1.txt";
        try {
            service = new FileDataImpl<TaillardInstance>(new TaillardFileImpl());
            service.getData(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
