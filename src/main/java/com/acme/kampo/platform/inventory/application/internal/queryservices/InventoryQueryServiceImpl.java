package com.acme.kampo.platform.inventory.application.internal.queryservices;

import com.acme.kampo.platform.inventory.application.queryservices.InventoryQueryService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllInventoryQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetInventoryByIdQuery;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link InventoryQueryService}.
 * Read-only transactions — never modifies state.
 */
@Service
@Transactional(readOnly = true)
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final InventoryRepository inventoryRepository;

    public InventoryQueryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Optional<Inventory> handle(GetInventoryByIdQuery query) {
        return inventoryRepository.findById(query.inventoryId());
    }

    @Override
    public List<Inventory> handle(GetAllInventoryQuery query) {
        return inventoryRepository.findAll();
    }
}