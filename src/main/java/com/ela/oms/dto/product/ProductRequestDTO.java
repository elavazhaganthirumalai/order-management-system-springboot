package com.ela.oms.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDTO {
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotNull
    private int quantity;
    @NotNull
    private long productReference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductReference() {
        return productReference;
    }

    public void setProductReference(long productReference) {
        this.productReference = productReference;
    }
}
