package logic.parser;


import logic.exceptions.ParserException;

/**
 * Interfaz FileParser.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public interface FileParser<T> {

    String readFile(String name) throws ParserException;

    T parseFile(String data) throws ParserException;
}
