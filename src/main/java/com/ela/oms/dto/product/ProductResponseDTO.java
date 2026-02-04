package com.ela.oms.dto.product;

public class ProductResponseDTO {
    private long id;
    private String name;
    private double price;
    private int quantity;
    private long productReference;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
