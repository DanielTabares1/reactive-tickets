package com.dani.reactive_tickets.infrastructure.adapter.out.persistence.repository;

import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventR2dbcRepository extends R2dbcRepository<EventEntity, Integer> {

    Flux<EventEntity> findAllBy(Pageable pageable);

    Mono<Long> count();

}
