package logic.output;

import logic.instances.Instance;
import java.util.List;

/**
 * Interfaz Writer que gestiona la escritura de ficheros de datos.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public interface Writer {

    void writeMatrixStartTimes(String path, String name, String sheetName, List<String[]> data);

    void write(String path, String name, String instName, int rowNum, int colNum, Instance inst,
               long result, boolean extended, String objFunc);
}
