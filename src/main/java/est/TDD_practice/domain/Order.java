package est.TDD_practice.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Customer customer;
    private final List<OrderItem> items;

    public Order(Customer customer, List<OrderItem> items) {
        this.customer = customer;
        this.items = items;
    }

    public int calculateTotalAmount() {
        int totalPrice =  items.stream()
                .mapToInt(OrderItem::getSubtotal)
                .sum();

        if (customer.isVip()) {
            totalPrice *= 0.9;
        }

        return totalPrice;
    }
}
