package org.example.car.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "car", schema = "public")
public class Car extends IdAndSystemProperties {

    @Column(name = "reg_number", length = 10, unique = true)
    private String regNumber;

    @Column(name = "login", length = 100)
    private String login;

//    @Column(name = "recomended_oil_type_id", length = 50)
//    private String recomendedOilTypeId;
//
//    @Column(name = "recomended_oil_viscosity_id", length = 50)
//    private String recomendedOilViscosityId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "motor_type_id", foreignKey = @ForeignKey(name = "fk_car_motor_type_id"))
    private MotorType motorType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "mark_model_id", foreignKey = @ForeignKey(name = "fk_car_mark_model_id"))
    private MarkModel markModel;

}
