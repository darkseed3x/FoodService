package ru.vvzl.fs.rs.model;

import java.time.OffsetDateTime;

public class OrderDTO {
    private OffsetDateTime created;
    private int orderId;

    public OrderDTO(OffsetDateTime created, int orderId) {
        this.created = created;
        this.orderId = orderId;
    }

    public OrderDTO() {
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
