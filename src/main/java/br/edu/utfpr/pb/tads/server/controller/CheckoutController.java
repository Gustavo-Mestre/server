package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.dto.CheckoutDto;
import br.edu.utfpr.pb.tads.server.dto.OrderItemsDto;
import br.edu.utfpr.pb.tads.server.model.OrderItemsModel;
import br.edu.utfpr.pb.tads.server.model.OrderModel;
import br.edu.utfpr.pb.tads.server.model.ProductModel;
import br.edu.utfpr.pb.tads.server.model.UserModel;
import br.edu.utfpr.pb.tads.server.repository.ItemOrderRepository;
import br.edu.utfpr.pb.tads.server.repository.OrderRepository;
import br.edu.utfpr.pb.tads.server.repository.ProductRepository;
import br.edu.utfpr.pb.tads.server.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
public class CheckoutController {

    @Autowired
    ItemOrderRepository itemOrderRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/checkout/complete")
    public ResponseEntity<?> checkoutComplete(@RequestBody @Valid CheckoutDto checkoutDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserModel user = userRepository.findUserByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Primeiro, criação de um novo pedido.
        OrderModel newOrder = new OrderModel();
        newOrder.setDate(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder = orderRepository.save(newOrder);

        // Iteração sobre os itens do pedido.
        for (OrderItemsDto item : checkoutDto.items()) {
            OrderItemsModel itemOrderModel = new OrderItemsModel();
            ProductModel product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            itemOrderModel.setProduct(product);
            itemOrderModel.setPrice(item.price());
            itemOrderModel.setAmount(item.amount());
            itemOrderModel.setOrder(newOrder);

            itemOrderRepository.save(itemOrderModel);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
}
