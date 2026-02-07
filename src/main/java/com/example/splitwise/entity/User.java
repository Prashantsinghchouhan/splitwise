package com.example.splitwise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
public class User extends BaseEntity {
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
}
