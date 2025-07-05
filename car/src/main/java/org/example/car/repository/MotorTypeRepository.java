package org.example.car.repository;

import org.example.car.persistence.entity.MotorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MotorTypeRepository extends JpaRepository<MotorType, UUID> {
List<MotorType> findAllByActual(Boolean actual);
}
