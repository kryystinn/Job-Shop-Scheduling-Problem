package logic.output;

import logic.schedule.rules.Rule;

import java.util.List;

public interface Writer {

    public void write(String path, String name, String sheetName, List<String[]> data);

    public void writeAllSameSheet(String path, String name, String instName, int rowNum, int result, int rule,
                                  boolean extended);
}
