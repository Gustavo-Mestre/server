package br.edu.utfpr.pb.tads.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private UserModel userModel;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductModel product = (Product) o;
//        return Objects.equals(id, product.id);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
