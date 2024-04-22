package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.model.CategoryModel;
import br.edu.utfpr.pb.tads.server.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryControllerTest {

    private static final String API_CATEGORIES = "/categories";

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        // Configuração padrão para o repositório mockado
        List<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel(1L, "Category 1"));
        categories.add(new CategoryModel(2L, "Category 2"));
        when(categoryRepository.findAll()).thenReturn(categories);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new CategoryModel(1L, "Category 1")));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(new CategoryModel(2L, "Category 2")));

        when(categoryRepository.save(any(CategoryModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void getAllCategories_receiveListOfCategories() {
        ResponseEntity<List<CategoryModel>> response = categoryController.getAllCategories();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    public void getOneCategory_whenExists_receiveCategory() {
        ResponseEntity<Object> response = categoryController.getOneCategory(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((CategoryModel) response.getBody()).getCategoryName()).isEqualTo("Category 1");
    }

    @Test
    public void getOneCategory_whenDoesNotExist_receiveNotFound() {
        ResponseEntity<Object> response = categoryController.getOneCategory(3L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
