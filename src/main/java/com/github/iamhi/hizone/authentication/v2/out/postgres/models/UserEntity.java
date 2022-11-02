package com.github.iamhi.hizone.authentication.v2.out.postgres.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class UserEntity {

    @Id
    @Column("id")
    Integer id;

    @Column("uuid")
    String uuid;

    @Column("username")
    String username;

    @Column("password")
    String password;

    @Column("email")
    String email;

    @Column("roles")
    String roles;

    @Column("created_at")
    Instant createdAt;

    @Column("updated_at")
    Instant updatedAt;

    public UserEntity(String uuid, String username, String password, String email, String roles, Instant createdAt, Instant updatedAt) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserEntity(String uuid, String username, String email, String roles) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
