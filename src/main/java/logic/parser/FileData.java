package logic.parser;

import logic.exceptions.ParserException;

/**
 * Interfaz FileData.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public interface FileData<T> {

    T getData(String filename) throws ParserException;
}
