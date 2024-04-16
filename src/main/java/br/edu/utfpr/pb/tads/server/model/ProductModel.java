package br.edu.utfpr.pb.tads.server.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;

@Entity
@Table(name = "TB_PRODUCTS")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder

public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String urlImage;

//    //Recebe o ID da category como chave estrangeira.
//    @ManyToOne
//    @JoinColumn(name = "CATEGORY_ID")
//    private CategoryModel category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

//    public CategoryModel getCategory() {
//        return category;
//    }
//
//    public void setCategory(CategoryModel category) {
//        this.category = category;
//    }

}
