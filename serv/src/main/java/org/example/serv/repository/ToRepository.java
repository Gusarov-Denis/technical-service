package org.example.serv.repository;

import org.example.serv.persistence.entity.To;
import org.example.serv.persistence.entity.ToStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToRepository extends JpaRepository<To, UUID> {
List<To> findAllByActual(Boolean actual);
Optional<To> findByNameAndActual(String name, Boolean actual);

    @Query("select p.id from #{#entityName} p where p.actual = true and ((p.dateTo is null and p.toStatus.id = :notRequiredStatusId and p.mileage < :startMileage) or (p.dateTo < :dateTo and p.toStatus.id = :doneStatusId ))")
    List<UUID> getIds(@Param("notRequiredStatusId") UUID notRequiredStatusId,
                      @Param("startMileage") Integer startMileage,
                      @Param("dateTo") ZonedDateTime dateTo,
                      @Param("doneStatusId") UUID doneStatusId);
}
