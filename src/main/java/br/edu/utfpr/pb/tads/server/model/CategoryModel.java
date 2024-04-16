package br.edu.utfpr.pb.tads.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_CATEGORY")

public class CategoryModel {

    //Gerar chave primária do tipo serial.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    //Define a categoria como não nula e limita o tamanho de 0 a 100.
    @NotNull
    @Size(min = 2, max = 100)
    private String category_name;

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
