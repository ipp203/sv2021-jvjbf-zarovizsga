package org.training360.finalexam;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import java.net.URI;

public class EntityNotFoundException extends AbstractThrowableProblem {

    public EntityNotFoundException(URI type, String title, String detail) {
        super(type, title, Status.NOT_FOUND, detail);
    }
}
