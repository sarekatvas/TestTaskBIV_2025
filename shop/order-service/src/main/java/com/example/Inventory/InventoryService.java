package com.example.Inventory;
import java.util.UUID;  
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import com.example.dto.ProductDto;


@Path("/api/products")
@RegisterRestClient(configKey = "inventory-service")
public interface InventoryService {

    @GET
    @Path("/{id}")
    ProductDto getProductById(@PathParam("id") UUID id);

    @PUT
    @Path("/{id}/stock")
    void updateStock(@PathParam("id") UUID id, @QueryParam("delta") int delta);

    @PUT
    @Path("/{id}/reserve")
    void reserveProduct(@PathParam("id") UUID id, @QueryParam("quantity") int quantity);

    @PUT
    @Path("/{id}/cancel-reservation")
    void cancelReservation(@PathParam("id") UUID id, @QueryParam("quantity") int quantity);

    @GET
    @Path("/by-name/{name}")
    ProductDto getProductByName(@PathParam("name") String name);
}