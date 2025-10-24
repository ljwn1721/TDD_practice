package est.TDD_practice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequest {
    private Long productId;
    private int quantity;

    public OrderRequest(Long id, int quantity) {
        this.productId = id;
        this.quantity = quantity;
    }
}
