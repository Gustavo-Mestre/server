package br.edu.utfpr.pb.tads.server.dto;

import br.edu.utfpr.pb.tads.server.model.user.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String name, @NotNull String password, UserRole role) {
}
