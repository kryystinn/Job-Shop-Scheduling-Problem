package logic.parser.impl;

import logic.exceptions.ParserException;
import logic.parser.FileData;
import logic.parser.FileParser;

public class FileDataImpl<T> implements FileData {
    private FileParser<T> parser;
    public FileDataImpl(FileParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public Object getData(String filename) throws ParserException {
        return parser.parseFile(parser.readFile(filename));
    }
}
