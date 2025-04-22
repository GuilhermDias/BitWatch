package com.guilherme.bitWatch.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record RequestUser(String name, String lastName,
                          @Email String email,
                          @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}") String document,
                          String password) {
}
