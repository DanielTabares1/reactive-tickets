package com.dani.reactive_tickets.infrastructure.adapter.out.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table("event")
public class EventEntity {

    @Id
    private Integer id;

    private String name;

    private String description;

    @Column("event_date")
    private Instant eventDate;

    private String site;

    private String status;

    @Column("image_url")
    private String imageUrl;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Version
    private Long version;

}
