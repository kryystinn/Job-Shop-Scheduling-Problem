package logic.parser.impl;

import logic.exceptions.ParserException;
import logic.parser.FileParser;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileParserImpl<T> implements FileParser<T> {

    @Override
    public String readFile(String name) throws ParserException {
        try {
            if (!name.toLowerCase().endsWith(".txt"))
                throw new IllegalArgumentException();
            return new String(Files.readAllBytes(Paths.get(name)));
        }   catch (FileNotFoundException e) {
            throw new ParserException("File does not exist.");
        }   catch (NullPointerException e) {
            throw new ParserException("File is null.");
        }   catch (Exception e) {
            throw new ParserException(e);
        }
    }

    @Override
    public abstract T parseFile(String data) throws ParserException;
}
