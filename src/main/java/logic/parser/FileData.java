package logic.parser;

import logic.exceptions.ParserException;

import java.io.FileNotFoundException;

public interface FileData<T> {

    public T getData(String filename) throws ParserException;
}
