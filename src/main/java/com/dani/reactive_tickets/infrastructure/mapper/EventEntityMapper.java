package com.dani.reactive_tickets.infrastructure.mapper;

import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.domain.model.vo.EventStatus;
import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventEntityMapper {
    public Event toDomain(EventEntity eventEntity){
        return Event.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .description(eventEntity.getDescription())
                .eventDate(eventEntity.getEventDate())
                .site(eventEntity.getSite())
                .eventStatus(EventStatus.valueOf(eventEntity.getStatus()))
                .imageUrl(eventEntity.getImageUrl())
                .createdAt(eventEntity.getCreatedAt())
                .updatedAt(eventEntity.getUpdatedAt())
                .version(eventEntity.getVersion())
                .build();
    }
}
