package logic.schedule.rules;

import logic.instances.Operation;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz Rule que representa una regla de prioridad.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public interface Rule extends Serializable {

    Operation run(List<Operation> operations);
}
