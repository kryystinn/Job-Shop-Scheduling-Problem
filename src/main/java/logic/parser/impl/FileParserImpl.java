package logic.parser.impl;

import logic.exceptions.ParserException;
import logic.instances.TaillardInstance;
import logic.parser.FileParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileParserImpl<T> implements FileParser<T> {
    @Override
    public String readFile(String name) throws ParserException {
        try {
            return new String(Files.readAllBytes(Paths.get(name)));
        } catch (FileNotFoundException e) {
            throw new ParserException("File does not exist");
        }  catch (Exception e) {
            throw new ParserException(e);
        }
    }

    @Override
    public abstract T parseFile(String data) throws ParserException;
}
