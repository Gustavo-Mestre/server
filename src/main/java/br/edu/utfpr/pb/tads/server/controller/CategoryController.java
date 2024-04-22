package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.model.CategoryModel;
import br.edu.utfpr.pb.tads.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryModel>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable(value = "id") Long id){
        Optional<CategoryModel> category = categoryRepository.findById(id);
        if (category.isEmpty()) return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("User not found.");
        return ResponseEntity.status(HttpStatus.OK).body(category.get());
    }
}
