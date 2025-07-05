package org.example.oil.repository;

import org.example.oil.persistence.entity.Oil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OilRepository extends JpaRepository<Oil, UUID> {
List<Oil> findAllByActual(Boolean actual);
Optional<Oil> findByNameAndActual(String name, Boolean actual);
}
