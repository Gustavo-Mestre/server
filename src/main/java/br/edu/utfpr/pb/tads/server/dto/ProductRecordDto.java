package br.edu.utfpr.pb.tads.server.dto;

import br.edu.utfpr.pb.tads.server.model.CategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDto(@NotBlank String name, String description, @NotNull BigDecimal price,
                                String urlImage /*CategoryModel category*/) {

}
