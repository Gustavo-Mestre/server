package br.edu.utfpr.pb.tads.server.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import br.edu.utfpr.pb.tads.server.model.OrderModel;
import br.edu.utfpr.pb.tads.server.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class OrderControllerTest {

    private static final String API_ORDERS = "/order";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void cleanup() {
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    public void getAllOrders_whenNoOrdersInDatabase_receiveEmptyList() {
        orderRepository.deleteAll();

        ResponseEntity<OrderModel[]> response = testRestTemplate.getForEntity(API_ORDERS, OrderModel[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }
}
