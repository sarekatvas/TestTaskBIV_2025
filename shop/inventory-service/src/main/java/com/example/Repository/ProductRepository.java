package com.example.Repository;
import java.util.Optional;
import java.util.UUID;

import com.example.Model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    public Optional<Product> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }
}
