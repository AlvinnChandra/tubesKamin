package com.example.login;

import java.util.Optional;

public interface LoginRepository {
    Optional<LoginData> findUser(String nama);
}
