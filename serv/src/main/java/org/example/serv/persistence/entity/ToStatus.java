package org.example.serv.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "to_status", schema = "public")
public class ToStatus {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @Column(name = "sys_name", length = 50, unique = true)
    private String sysName;

    @Column(name = "actual")
    private Boolean actual;
}
