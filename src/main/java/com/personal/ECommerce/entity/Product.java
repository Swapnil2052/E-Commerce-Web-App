package com.personal.ECommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cat_id", referencedColumnName = "id")
    private Category category;

    private double price;

    private double weight;

    private String description;
}
