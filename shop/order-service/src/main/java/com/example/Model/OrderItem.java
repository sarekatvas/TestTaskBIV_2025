package com.example.Model;
import java.math.BigDecimal;
import java.util.UUID;
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
    public UUID id;

    @ManyToOne
    @JoinColumn(name ="order_id", nullable=false)
    public Order order;

    @Column (name="product_id", nullable = false)
    public UUID productId;
    
    @Column(nullable = false)
    public Integer quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    public OrderItem() {
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));}
}
