package com.romco.bracketeer.service;

import lombok.NonNull;

public interface UserService {
    boolean registerUser(@NonNull String username,
                         @NonNull String password,
                         @NonNull String email);
}
