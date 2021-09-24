package logic.schedule.rules;

import logic.instances.*;
import java.util.List;
import java.util.Optional;

public interface Rule {
    Operation run(List<Operation> operations);
}
