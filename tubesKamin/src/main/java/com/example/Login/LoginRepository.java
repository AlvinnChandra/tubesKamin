package com.example.Login;

import java.util.Optional;

public interface LoginRepository {
    Optional<LoginData> findUser(String nama);
}
