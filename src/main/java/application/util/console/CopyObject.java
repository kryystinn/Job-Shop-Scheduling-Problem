package application.util.console;

import logic.schedule.rules.Rule;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;

/* Utility for making deep copies (vs. clone()'s shallow copies) of objects.
 * Objects are first serialized and then deserialized. Error checking is fairly
 * minimal in this implementation. If an object is encountered that cannot be
 * serialized (or that references an object that cannot be serialized) an error
 * is printed to System.err and null is returned. Depending on your specific
 * application, it might make more sense to have copy(...) re-throw the
 * exception.
 *
 * A later version of this class includes some minor optimizations.
 */
public class CopyObject {

/* Returns a copy of the object, or null if the object cannot be serialized.
 */
    public static Rule copy(Rule orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return (Rule) obj;
    }

}
