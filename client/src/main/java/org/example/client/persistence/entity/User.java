package org.example.client.persistence.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
@Table(name = "client_users", schema = "public")
public class User {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "login", length = 100, unique = true)
    private String login;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "email", length = 255, unique = true)
    private String email;
}
