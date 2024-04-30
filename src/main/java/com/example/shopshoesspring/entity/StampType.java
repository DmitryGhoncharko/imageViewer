package com.example.shopshoesspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "stamp_type")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class StampType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stamp_type_id", nullable = false)
    private Long id;

    @Column(name = "stamp_type_name", nullable = false, length = 500)
    private String stampTypeName;

}