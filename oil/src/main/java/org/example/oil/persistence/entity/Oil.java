package org.example.oil.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "oil", schema = "public")
public class Oil extends IdAndSystemProperties {

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oil_type_id", foreignKey = @ForeignKey(name = "fk_oil_oil_type_id"))
    private OilType oilType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oil_viscosity_id", foreignKey = @ForeignKey(name = "fk_oil_oil_viscosity_id"))
    private OilViscosity oilViscosity;

}
