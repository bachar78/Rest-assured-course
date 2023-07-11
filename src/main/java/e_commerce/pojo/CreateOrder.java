package e_commerce.pojo;

import java.util.List;

public class CreateOrder {
    private List<Order> orders;

    public CreateOrder(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
