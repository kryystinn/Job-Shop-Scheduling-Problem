package logic.schedule.rules;

import logic.instances.*;
import java.util.List;

public interface Rule {
    Operation run(List<Operation> operations);
}
