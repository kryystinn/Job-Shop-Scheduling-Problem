package logic.output.impl;

import logic.output.Writer;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CvsWriterImpl implements Writer {
    private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";

    @Override
    public void write(String filename) {
        /**
        try (
                //Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
                break;
                //CSVWriter csvWriter = new CSVWriter(writer,
                //       CSVWriter.DEFAULT_SEPARATOR,
                //     CSVWriter.NO_QUOTE_CHARACTER,
                //   CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                // CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"Name", "Email", "Phone", "Country"};

        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }
}
