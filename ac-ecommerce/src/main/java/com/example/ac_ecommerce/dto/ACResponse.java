package com.example.ac_ecommerce.dto;

import lombok.Data;

@Data
public class ACResponse {
    private Long id;
    private String name;
    private String brand;
    private String category;
    private String capacity;
    private Double price;
    private String image;
}
