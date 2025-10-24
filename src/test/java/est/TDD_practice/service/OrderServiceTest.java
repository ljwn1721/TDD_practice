package est.TDD_practice.service;

import static org.junit.jupiter.api.Assertions.*;

import est.TDD_practice.OrderRequest;
import est.TDD_practice.domain.Customer;
import est.TDD_practice.domain.Order;
import est.TDD_practice.domain.OrderItem;
import est.TDD_practice.domain.Product;
import est.TDD_practice.exception.InsufficientStockException;
import est.TDD_practice.exception.MinimumOrderAmountException;
import est.TDD_practice.repository.OrderRepository;
import est.TDD_practice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("재고가 충분하면 주문에 성공한다")
    void createOrderWithSufficientStock() {
        // given
        Product product = new Product("커피", 5000, 10);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        Customer customer = new Customer("홍길동", false);
        OrderRequest orderRequest = new OrderRequest(1L, 2); // 5000 * 2 = 10000

        // when
        Order order = orderService.createOrder(customer, List.of(orderRequest));

        // then
        assertThat(order.calculateTotalAmount()).isEqualTo(10000);
        verify(orderRepository).save(order);
        assertThat(product.getStockQuantity()).isEqualTo(8); // 10 - 2
    }

    @Test
    @DisplayName("재고가 부족하면 예외가 발생한다")
    void insufficientStockThrowsException() {
        // given
        Product product = new Product("커피", 5000, 1);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        Customer customer = new Customer("홍길동", false);
        OrderRequest orderRequest = new OrderRequest(1L, 5);

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(customer, List.of(orderRequest)))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("재고가 부족합니다");
    }

    @Test
    @DisplayName("최소 주문 금액 미달 시 예외가 발생한다")
    void minimumOrderAmountValidation() {
        // TODO: 10,000원 미만 주문
        // given
        Product product = new Product("쿠키", 3000, 10);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        Customer customer = new Customer("홍길동", false);
        OrderRequest orderRequest = new OrderRequest(1L, 2); // 6000원

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(customer, List.of(orderRequest)))
                .isInstanceOf(MinimumOrderAmountException.class)
                .hasMessageContaining("최소 주문 금액은 10000원입니다");
    }
}