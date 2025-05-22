package com.example.Model;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.example.Enum.*;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "customer_name", nullable = false)
    public String customerName;

    @Column(name = "customer_email", nullable = false) 
    public String customerEmail;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderItem> items;

    public Order() {
    }
}


