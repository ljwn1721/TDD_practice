package est.TDD_practice.domain;

import lombok.Getter;

@Getter
public class Product {
    private final String name;
    private final int price;
    private int stockQuantity;

    public Product(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void reduceStock(int quantity) {
        this.stockQuantity -= quantity;
    }
}