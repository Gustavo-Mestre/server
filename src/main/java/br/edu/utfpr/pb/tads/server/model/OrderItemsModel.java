package br.edu.utfpr.pb.tads.server.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private ProductModel product;

    private BigDecimal price;

    private Integer amount;
}


