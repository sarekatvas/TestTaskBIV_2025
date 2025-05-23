package com.example.Repository;
import java.util.Optional;
import java.util.UUID;
import com.example.Model.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Репозиторий для работы с заказами
 */

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
    public Optional<Order> findByIdOptional(UUID id){
        return find("id", id).firstResultOptional();
    }
}
