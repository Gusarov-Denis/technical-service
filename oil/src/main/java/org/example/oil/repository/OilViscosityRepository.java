package org.example.oil.repository;

import org.example.oil.persistence.entity.OilViscosity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OilViscosityRepository extends JpaRepository<OilViscosity, UUID> {
List<OilViscosity> findAllByActual(Boolean actual);
}
