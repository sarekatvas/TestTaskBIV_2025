package com.example.dto;

import java.util.List;

public class OrderRequest {
    public String customerName;
    public String customerEmail;
    public List<OrderItemRequest> items;
}
