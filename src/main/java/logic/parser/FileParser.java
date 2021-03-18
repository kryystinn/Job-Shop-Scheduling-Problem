package logic.parser;


import logic.exceptions.ParserException;

import java.io.FileNotFoundException;

public interface FileParser<T> {

    public String readFile(String name) throws ParserException;

    public T parseFile(String data) throws ParserException;
}
