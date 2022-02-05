package logic.parser.impl;

import logic.exceptions.ParserException;
import logic.parser.FileParser;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase abstracta FileParserImpl que implementa de {@link FileParser}.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public abstract class FileParserImpl<T> implements FileParser<T> {

    /**
     * Método que lee un fichero de texto.
     *
     * @param name nombre del fichero
     * @return texto con los datos del fichero
     * @throws ParserException si falla al tratar de leer el fichero
     */
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

    /**
     * Método que parsea los datos de un fichero.
     *
     * @param data datos del fichero a parsear
     * @return tipo genérico
     * @throws ParserException si falla al parsear el fichero
     */
    @Override
    public abstract T parseFile(String data) throws ParserException;
}
