package com.example.ac_ecommerce.dto;

import lombok.Data;

@Data
public class ACRequest {
    private String name;
    private String brand;
    private String category;      // e.g., "Split AC", "Window AC"
    private String capacity;      // e.g., "1.5 Ton"
    private Double price;
}