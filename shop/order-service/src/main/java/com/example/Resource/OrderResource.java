package com.example.Resource;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.Inventory.InventoryService;
import com.example.Model.*;
import com.example.Repository.OrderRepository;
import com.example.dto.ProductDto;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.example.Enum.*;

@Path("/orders") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    @Inject
    OrderRepository orderRepository;
    
    @Inject
    @RestClient
    InventoryService inventoryService;

    @GET
    @Operation(
        summary = "Посмотреть все заказы"
    )
    public Response getAllOrders() {
        return Response.ok(orderRepository.listAll()).build();
    }

    @GET
    @Operation(
        summary = "Посмотреть заказ по ID"
    )
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") UUID id){
        try{
        return orderRepository.findByIdOptional(id)
            .map(order ->Response.ok(order).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
        }
       catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при получении заказа: "+ e.getMessage())).build();
       }
    }

    @POST
    @Operation(
        summary = "Добавить новый заказ"
    )
    @Transactional
    public Response createOrder(OrderR orderRequest) {
        try {
            Order order = new Order();
            //order.id = UUID.randomUUID();
            order.customerName = orderRequest.customerName;
            order.customerEmail = orderRequest.customerEmail;
            order.createdAt = Instant.now();
            order.status = OrderStatus.CREATED;

            // Создаем элементы заказа
            for (OrderItemRequest itemRequest : orderRequest.items) {
                ProductDto product = inventoryService.getProductById(itemRequest.productId);

                if(product == null){
                    throw new WebApplicationException("Заказ не найден: "+ itemRequest.productId, Response.Status.BAD_REQUEST);
                }
                if(product.quantity<itemRequest.quantity){
                    throw new WebApplicationException("На складе недостаточно товара: "+ itemRequest.quantity, Response.Status.BAD_REQUEST);
                }

                OrderItem item = new OrderItem();
                item.productId = itemRequest.productId;
                item.quantity = itemRequest.quantity;
                item.price = BigDecimal.valueOf(product.price); 
                item.order = order;
                
                order.items.add(item);
            }

            // Сохраняем заказ
            orderRepository.persist(order);
            
            return Response.status(Response.Status.CREATED).entity(order).build();
        } catch (Exception e) {
            return Response.serverError()
                .entity(Map.of("error", "Ошибка при создании заказа: " + e.getMessage()))
                .build();
        }
    }

    @DELETE
    @Operation(
        summary = "Удалить заказ по ID"
    )
    @Transactional
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") UUID id){
        try{
        Order order = orderRepository.findByIdOptional(id).
        orElseThrow(() -> new WebApplicationException("Заказ не найден",Response.Status.NOT_FOUND));

        for (OrderItem item: order.items){
            inventoryService.cancelReservation(item.productId, item.quantity);
        }
        return Response.ok((order)).build();
    }
    catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при удалении заказа: "+ e.getMessage())).build();
    }
    }

    public static class OrderR {
        public String customerName;
        public String customerEmail;
        public List<OrderItemRequest> items;
    }
    
    public static class OrderItemRequest {
        public UUID productId;
        public Integer quantity;
    }

}
