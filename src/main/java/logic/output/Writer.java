package logic.output;

import logic.instances.Instance;
import java.util.List;

public interface Writer {

    public void writeMatrixStartTimes(String path, String name, String sheetName, List<String[]> data);

    public void write(String path, String name, String instName, int rowNum, int colNum, Instance inst,
                      long result, boolean extended, String objFunc);
}
