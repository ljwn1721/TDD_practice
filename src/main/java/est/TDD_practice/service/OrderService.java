package est.TDD_practice.service;


import est.TDD_practice.OrderRequest;
import est.TDD_practice.domain.Customer;
import est.TDD_practice.domain.Order;
import est.TDD_practice.domain.OrderItem;
import est.TDD_practice.domain.Product;
import est.TDD_practice.exception.InsufficientStockException;
import est.TDD_practice.exception.MinimumOrderAmountException;
import est.TDD_practice.repository.OrderRepository;
import est.TDD_practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public Order createOrder(Customer customer, List<OrderRequest> requests) {
        List<OrderItem> items = new ArrayList<>();

        // 재고 확인 및 수량 재설정
        for (OrderRequest req : requests) {
            Product product = productRepository.findById(req.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID=" + req.getProductId()));

            validateStock(product, req.getQuantity());

            product.reduceStock(req.getQuantity());
            productRepository.save(product);

            items.add(new OrderItem(product, req.getQuantity()));
        }

        // 총액 계산
        Order order = new Order(customer, items);
        int total = order.calculateTotalAmount();

        // 최소 주문 금액 검증
        validateMinimumAmount(total);

        // 저장 후 반환
        orderRepository.save(order);
        return order;
    }

    private void validateStock(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException("재고가 부족합니다: " + product.getName());
        }
    }

    private void validateMinimumAmount(int totalAmount) {
        if (totalAmount < 10000) {
            throw new MinimumOrderAmountException("최소 주문 금액은 10000원입니다.");
        }
    }
}