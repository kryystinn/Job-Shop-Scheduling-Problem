package logic.schedule.rules;

import logic.instances.Operation;

import java.io.Serializable;
import java.util.List;

public interface Rule extends Serializable {

    Operation run(List<Operation> operations);
}
