    package br.edu.utfpr.pb.tads.server.dto;

    import jakarta.validation.constraints.NotNull;

    public record LoginRecordDto(@NotNull (message = "Name must not be null") String name, @NotNull (message = "Password must not be null") String password) {

    }
