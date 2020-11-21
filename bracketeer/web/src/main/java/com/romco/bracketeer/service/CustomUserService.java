package com.romco.bracketeer.service;

import lombok.NonNull;

public interface CustomUserService {
    boolean registerUser(@NonNull String username,
                         @NonNull String password,
                         @NonNull String email);
}
