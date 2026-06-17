package com.acme.kampo.platform.field.application.acl;

import com.acme.kampo.platform.field.application.queryservices.FieldVisitQueryService;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitsByFieldQuery;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.field.interfaces.acl.FieldOperationContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link FieldOperationContextFacade}.
 *
 * <p>Returns the most recent field visit ID for a given field —
 * the list comes ordered by {@code scheduledAt DESC} from the repository.</p>
 */
@Service
public class FieldOperationContextFacadeImpl implements FieldOperationContextFacade {

    private final FieldVisitQueryService fieldVisitQueryService;

    public FieldOperationContextFacadeImpl(FieldVisitQueryService fieldVisitQueryService) {
        this.fieldVisitQueryService = fieldVisitQueryService;
    }

    @Override
    public Optional<Long> fetchFieldVisitIdBy(Long fieldId) {
        return fieldVisitQueryService
                .handle(new GetFieldVisitsByFieldQuery(FieldId.of(fieldId)))
                .stream()
                .findFirst()
                .map(fv -> fv.getId().getValue());
    }
}