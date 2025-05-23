package com.example.Model;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Сущность товара
*/
@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {
    // идентификатор товара
    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    public UUID id;
    
    // наименование товара. Генерируется автоматически. Только для чтения
    @Column(nullable = false)
    public String name;
    
    // дополнительная информация
    @Column
    public String description;
    
    // цена за единицу продукции
    @Column(nullable = false)
    public Double price;
    
    // количество на складе
    @Column(nullable = false)
    public Integer quantity;
}