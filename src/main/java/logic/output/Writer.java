package logic.output;

import logic.instances.Instance;
import logic.schedule.rules.Rule;

import java.util.List;

public interface Writer {

    public void writeMatrix(String path, String name, String sheetName, List<String[]> data);

    public void writeAllSameSheet(String path, String name, String instName, int rowNum, int colNum, Instance inst,
                                  long result, boolean extended, String objFunc);
}
