package org.example.serv.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "to", schema = "public")
public class To extends IdAndSystemProperties {

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @Column(name = "login", length = 100)
    private String login;

    @Column(name = "oil_id", columnDefinition = "uuid")
    private UUID oilId;

    @Column(name = "car_id", columnDefinition = "uuid")
    private UUID carId;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "date_to", nullable = true, columnDefinition = "timestamptz")
    ZonedDateTime dateTo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "to_status_id", foreignKey = @ForeignKey(name = "fk_to_to_status_id"))
    private ToStatus toStatus;

}
