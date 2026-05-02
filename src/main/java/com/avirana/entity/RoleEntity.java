package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_name_org",
                columnNames = {"flag_id", "org"}
        )
})
public class RoleEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String organization;
}
