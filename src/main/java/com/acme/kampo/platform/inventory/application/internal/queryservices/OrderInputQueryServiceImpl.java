package com.acme.kampo.platform.inventory.application.internal.queryservices;

import com.acme.kampo.platform.inventory.application.queryservices.OrderInputQueryService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllOrderInputsQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetOrderInputByIdQuery;
import com.acme.kampo.platform.inventory.domain.repositories.OrderInputRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link OrderInputQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class OrderInputQueryServiceImpl implements OrderInputQueryService {

    private final OrderInputRepository orderInputRepository;

    public OrderInputQueryServiceImpl(OrderInputRepository orderInputRepository) {
        this.orderInputRepository = orderInputRepository;
    }

    @Override
    public Optional<OrderInput> handle(GetOrderInputByIdQuery query) {
        return orderInputRepository.findById(query.orderId());
    }

    @Override
    public List<OrderInput> handle(GetAllOrderInputsQuery query) {
        return orderInputRepository.findAll();
    }
}
