package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.model.ProductModel;
import br.edu.utfpr.pb.tads.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //Usado para listar todos os produtos.
    @GetMapping("/product")
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        return ResponseEntity.status(HttpStatus.OK /*Status 200*/).body(productRepository.findAll());
    }

    //Usado para listar um produto através do ID.
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable (value = "id") Long id) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    //Filtragem dos produtos por categoria.
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductModel> products = productRepository.findByCategory_CategoryId(categoryId);
        if(products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(products);
    }



}
