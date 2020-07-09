package com.gb.arzek.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "number")
    private Long number;

    @Column(name = "client_id")
    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Order(Product product, Long number, Client client) {
        this.productId=product.getId();
        this.clientId=client.getId();
        this.number=number;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return String.format("Order [id = %d, product_id = %d, number=%d, client_id=%d]", id, productId, number, clientId);
    }
}
