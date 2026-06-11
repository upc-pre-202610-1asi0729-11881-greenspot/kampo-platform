package com.acme.kampo.platform.organization.application.internal.commandservices;

import com.acme.kampo.platform.organization.application.commandservices.OrganizationCommandService;
import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.commands.*;
import com.acme.kampo.platform.organization.domain.model.valueobjects.*;
import com.acme.kampo.platform.organization.domain.repositories.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final FundoRepository        fundoRepository;
    private final FieldRepository        fieldRepository;
    private final CropRepository         cropRepository;

    public OrganizationCommandServiceImpl(
            OrganizationRepository organizationRepository,
            FundoRepository fundoRepository,
            FieldRepository fieldRepository,
            CropRepository cropRepository) {
        this.organizationRepository = organizationRepository;
        this.fundoRepository        = fundoRepository;
        this.fieldRepository        = fieldRepository;
        this.cropRepository         = cropRepository;
    }

    @Override
    public Result<Organization, ApplicationError> handle(CreateOrganizationCommand command) {
        if (organizationRepository.existsByName(command.name()))
            return Result.failure(ApplicationError.conflict(
                    "ORGANIZATION",
                    "An organization named '%s' already exists".formatted(command.name())));
        try {
            var organization = organizationRepository.save(new Organization(command));
            return Result.success(organization);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(CreateOrganizationCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Organization, ApplicationError> handle(UpdateOrganizationCommand command) {
        var orgOpt = organizationRepository.findById(OrganizationId.of(command.organizationId()));
        if (orgOpt.isEmpty())
            return Result.failure(ApplicationError.notFound(
                    "ORGANIZATION", String.valueOf(command.organizationId())));
        try {
            var org = orgOpt.get();
            org.update(command);
            return Result.success(organizationRepository.save(org));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(UpdateOrganizationCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteOrganizationCommand command) {
        var id = OrganizationId.of(command.organizationId());
        if (!organizationRepository.existsById(id))
            return Result.failure(ApplicationError.notFound(
                    "ORGANIZATION", String.valueOf(command.organizationId())));
        try {
            organizationRepository.delete(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(DeleteOrganizationCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Fundo, ApplicationError> handle(RegisterFundoCommand command) {
        if (!organizationRepository.existsById(OrganizationId.of(command.organizationId())))
            return Result.failure(ApplicationError.notFound(
                    "ORGANIZATION", String.valueOf(command.organizationId())));
        try {
            var fundo = fundoRepository.save(new Fundo(command));
            return Result.success(fundo);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(RegisterFundoCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Field, ApplicationError> handle(RegisterFieldCommand command) {
        if (!fundoRepository.existsById(FundoId.of(command.fundoId())))
            return Result.failure(ApplicationError.notFound(
                    "FUNDO", String.valueOf(command.fundoId())));
        try {
            var field = fieldRepository.save(new Field(command));
            return Result.success(field);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(RegisterFieldCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Crop, ApplicationError> handle(RegisterCropCommand command) {
        if (!fieldRepository.existsById(FieldId.of(command.fieldId())))
            return Result.failure(ApplicationError.notFound(
                    "FIELD", String.valueOf(command.fieldId())));
        try {
            var crop = cropRepository.save(new Crop(command));
            return Result.success(crop);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrganizationCommandService.handle(RegisterCropCommand)", e.getMessage()));
        }
    }
}