package est.TDD_practice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    @DisplayName("주문 생성 시 총액을 계산한다")
    void calculateTotalAmount() {
        // TODO: Order 생성 및 총액 검증
        // given
        Product product1 = new Product("커피", 3000, 10);
        Product product2 = new Product("쿠키", 7000, 5);

        OrderItem item1 = new OrderItem(product1, 2); // 6000
        OrderItem item2 = new OrderItem(product2, 1); // 7000

        Order order = new Order(new Customer("홍길동", false), List.of(item1, item2));

        // when
        int totalAmount = order.calculateTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(13000);
    }

    @Test
    @DisplayName("VIP 고객은 10% 할인을 받는다")
    void vipDiscount() {
        // TODO: VIP 할인 적용 검증
        // given
        Product product1 = new Product("커피", 3000, 10);
        Product product2 = new Product("쿠키", 7000, 5);

        OrderItem item1 = new OrderItem(product1, 2); // 6000
        OrderItem item2 = new OrderItem(product2, 1); // 7000

        Order order = new Order(new Customer("홍길동", true), List.of(item1, item2));

        // when
        int totalAmount = order.calculateTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(11700);
    }
}