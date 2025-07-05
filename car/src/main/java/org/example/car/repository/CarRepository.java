package org.example.car.repository;

import org.example.car.persistence.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
List<Car> findAllByActual(Boolean actual);
Optional<Car> findByRegNumberAndActual(String regNumber, Boolean actual);
}
