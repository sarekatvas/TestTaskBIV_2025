package com.example.Mapper;
import com.example.dto.*;
import com.example.Model.*;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.id);
        dto.setName(product.name);
        dto.setQuantity(product.quantity);
        dto.setPrice(product.price);
        return dto;
    }
}