package org.example.serv.repository;

import org.example.serv.persistence.entity.ToStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToStatusRepository extends JpaRepository<ToStatus, UUID> {
List<ToStatus> findAllByActual(Boolean actual);
}
