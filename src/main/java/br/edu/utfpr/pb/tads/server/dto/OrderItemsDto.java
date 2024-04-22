package br.edu.utfpr.pb.tads.server.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemsDto(
        @NotNull Long productId,
        @NotNull BigDecimal price,
        @NotNull Integer amount
){
}
