package br.edu.utfpr.pb.tads.server.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRecordDto(@NotNull String name, @NotNull String password) {

}
