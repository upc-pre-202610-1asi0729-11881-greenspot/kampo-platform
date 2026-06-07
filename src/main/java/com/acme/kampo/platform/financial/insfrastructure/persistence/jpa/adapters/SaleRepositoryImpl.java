package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.adapters;


import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;
import com.acme.kampo.platform.financial.domain.repository.SaleRepository;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers.SalePersistenceAssembler;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories.SaleJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link SaleRepository} contract using Spring Data JPA.
 */
@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SaleRepositoryImpl(SaleJpaRepository jpaRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Sale save(Sale sale) {
        boolean isNew = sale.getId() == null;
        var entity    = SalePersistenceAssembler.toPersistenceFromDomain(sale);
        var saved     = jpaRepository.save(entity);
        var savedSale = SalePersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedSale.domainEvents().forEach(eventPublisher::publishEvent);
            savedSale.clearDomainEvents();
        }
        return savedSale;
    }

    @Override
    public Optional<Sale> findById(SaleId id) {
        return jpaRepository.findById(id.getValue())
                .map(SalePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Sale> findByFundoId(FundoId fundoId) {
        return jpaRepository.findByFundoId(fundoId.getValue()).stream()
                .map(SalePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Sale> findByCropName(String cropName) {
        return jpaRepository.findByCropName(cropName).stream()
                .map(SalePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void delete(SaleId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(SaleId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
