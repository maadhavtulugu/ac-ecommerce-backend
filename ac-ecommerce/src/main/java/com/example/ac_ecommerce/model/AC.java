package com.example.ac_ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category; // e.g., "Split AC", "Window AC"

    @Column(nullable = false)
    private String capacity; // e.g., "1.5 Ton"

    @Column(nullable = false)
    private Double price;

    private String image;
}

