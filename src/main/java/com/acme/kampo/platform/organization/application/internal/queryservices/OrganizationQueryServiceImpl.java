package com.acme.kampo.platform.organization.application.internal.queryservices;

import com.acme.kampo.platform.organization.application.queryservices.OrganizationQueryService;
import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.queries.*;
import com.acme.kampo.platform.organization.domain.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrganizationQueryServiceImpl implements OrganizationQueryService {

    private final OrganizationRepository organizationRepository;
    private final FundoRepository        fundoRepository;
    private final FieldRepository        fieldRepository;
    private final CropRepository         cropRepository;

    public OrganizationQueryServiceImpl(
            OrganizationRepository organizationRepository,
            FundoRepository fundoRepository,
            FieldRepository fieldRepository,
            CropRepository cropRepository) {
        this.organizationRepository = organizationRepository;
        this.fundoRepository        = fundoRepository;
        this.fieldRepository        = fieldRepository;
        this.cropRepository         = cropRepository;
    }

    @Override public Optional<Organization> handle(GetOrganizationByIdQuery query) { return organizationRepository.findById(query.organizationId()); }
    @Override public List<Organization>     handle(GetAllOrganizationsQuery query)  { return organizationRepository.findAll(); }
    @Override public Optional<Fundo>        handle(GetFundoByIdQuery query)          { return fundoRepository.findById(query.fundoId()); }
    @Override public List<Fundo>            handle(GetFundosByOrganizationQuery query){ return fundoRepository.findByOrganizationId(query.organizationId()); }
    @Override public Optional<Field>        handle(GetFieldByIdQuery query)          { return fieldRepository.findById(query.fieldId()); }
    @Override public List<Field>            handle(GetFieldsByFundoQuery query)       { return fieldRepository.findByFundoId(query.fundoId()); }
    @Override public Optional<Crop>         handle(GetCropByIdQuery query)            { return cropRepository.findById(query.cropId()); }
    @Override public List<Crop>             handle(GetCropsByFieldQuery query)        { return cropRepository.findByFieldId(query.fieldId()); }
}