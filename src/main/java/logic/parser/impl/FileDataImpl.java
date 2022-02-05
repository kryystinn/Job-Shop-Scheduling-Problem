package logic.parser.impl;

import logic.exceptions.ParserException;
import logic.parser.FileData;
import logic.parser.FileParser;

/**
 * Clase FileDataImpl que implementa de {@link FileData}.
 *
 * @param <T> tipo genérico
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class FileDataImpl<T> implements FileData {

    private final FileParser<T> parser;

    /**
     * Constructor de la clase {@link FileDataImpl}.
     *
     * @param parser parser del fichero
     */
    public FileDataImpl(FileParser<T> parser) {
        this.parser = parser;
    }

    /**
     * Método que obtiene los datos del fichero mediante el parser.
     *
     * @param filename nombre del fichero
     * @return un objeto con los datos del fichero
     * @throws ParserException si falla el parser
     */
    @Override
    public Object getData(String filename) throws ParserException {
        return parser.parseFile(parser.readFile(filename));
    }
}
