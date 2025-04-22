package com.guilherme.bitWatch.infra.security.auth;

public record CodeRequest(String email, String code) {
}
