package org.example.car.persistence.mappers;

import org.example.car.models.CarReq;
import org.example.car.persistence.entity.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    Car dtoToEntity(CarReq carReq);
    CarReq entityToDto(Car car);
}
