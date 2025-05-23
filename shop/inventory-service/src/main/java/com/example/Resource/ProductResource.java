package com.example.Resource;
import com.example.Model.Product;
import com.example.Repository.ProductRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import java.util.Map;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    @Inject
    ProductRepository productRepository;
    
    @GET
    @Operation(
        summary = "Посмотреть все товары"
    )
    public Response getAllProducts() {
        return Response.ok(productRepository.listAll()).build();
    }
    
    @GET
     @Operation(
        summary = "Посмотреть товар по ID"
    )
    @Path("/{id}")
    public Response getProductById(@PathParam("id") UUID id) {
        try{
            return productRepository.findByIdOptional(id)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
        }
        catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при получении товара: "+ e.getMessage())).build();
       }
    }
    
    @POST
     @Operation(
        summary = "Добавить новый товар"
    )
    @Transactional
    public Response createProduct(Product product) {
        try{
        productRepository.persist(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при добавлении товара: "+ e.getMessage())).build();
       }
    }
    
    @PUT
    @Transactional
     @Operation(
        summary = "Изменить товар"
    )
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") UUID id, Product product) {
       try{
        return productRepository.findByIdOptional(id)
            .map(existingProduct -> {
                existingProduct.name = product.name;
                existingProduct.description = product.description;
                existingProduct.price = product.price;
                existingProduct.quantity = product.quantity;
                productRepository.persist(existingProduct);
                return Response.ok(existingProduct).build();
            })
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
        }catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при изменении товара: "+ e.getMessage())).build();
       }
    }
    
    @DELETE
     @Operation(
        summary = "Удалить товар по ID"
    )
    @Path("/{id}")
    @Transactional
    public Response deleteProduct(@PathParam("id") UUID id){
        try{
        return productRepository.findByIdOptional(id)
        .map(product -> {
            productRepository.delete(product);
            return Response.ok(product).build();
        })
        .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }catch(Exception e){
        return Response.serverError().
        entity(Map.of("error","Ошибка при удалении товара: "+ e.getMessage())).build();
       }
    }
}