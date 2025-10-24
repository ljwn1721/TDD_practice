package est.TDD_practice.domain;

import lombok.Getter;

@Getter
public class Customer {
    private final String name;
    private final boolean isVip;

    public Customer(String name, boolean isVip) {
        this.name = name;
        this.isVip = isVip;
    }


}