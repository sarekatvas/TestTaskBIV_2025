package com.example.Model;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.example.Enum.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
/**
 * Сущность заказа
 */
@Entity
@Table(name = "Orders1")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    // имя заказчика 
    @Column(name = "customer_name", nullable = false)
    public String customerName;

    // контактные данные
    @Column(name = "customer_email", nullable = false) 
    public String customerEmail;

    // время создания заказа
    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    // статус заказа
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderStatus status;

    // продукция в заказе 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER,  orphanRemoval = true)
    @JsonManagedReference 
    public List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public UUID getId(){
        return id;
    }

    public String getEmail(){
        return customerEmail;
    }
}


