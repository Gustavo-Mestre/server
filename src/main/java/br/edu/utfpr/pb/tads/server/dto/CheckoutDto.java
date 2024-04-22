package br.edu.utfpr.pb.tads.server.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CheckoutDto(@NotNull List<OrderItemsDto> items) {
}

