package org.example.oil.repository;

import org.example.oil.persistence.entity.OilType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OilTypeRepository extends JpaRepository<OilType, UUID> {
List<OilType> findAllByActual(Boolean actual);
}
