package est.TDD_practice.domain;

import lombok.Getter;

@Getter
public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getSubtotal() {
        return product.getPrice() * quantity;
    }
}