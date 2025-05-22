package com.example.Model;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @ManyToOne
    @JoinColumn(name ="order_id", nullable=false)
    @JsonBackReference
    public Order order;

    // идентификатор продукции
    @Column (name="product_id", nullable = false)
    public UUID productId;
    
    // количество
    @Column(nullable = false)
    public Integer quantity;

    // цена
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    public OrderItem() {
    }

    // общая стоимость заказа
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));}
}
