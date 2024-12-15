package com.personal.ECommerce.dto;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    private String name;

    private String imageName;

    private int categoryId;

    private double price;

    private double weight;

    private String description;
}
