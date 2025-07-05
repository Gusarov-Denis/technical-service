package org.example.car.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class IdAndSystemProperties {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "actual")
    private Boolean actual;

    @Version
    private Long version;

    @Column(name = "date_created", nullable = true, columnDefinition = "timestamptz")
    ZonedDateTime dateCreated;

    @Column(name = "last_updated", nullable = true, columnDefinition = "timestamptz")
    ZonedDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        beforeSave();
    }

    @PreUpdate
    public void preUpdate() {
        beforeSave();
    }

    private void beforeSave() {

        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        if (dateCreated == null) {
            dateCreated = now;
        }
        lastUpdated = now;
    }
}
